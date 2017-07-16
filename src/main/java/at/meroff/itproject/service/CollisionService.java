package at.meroff.itproject.service;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.service.dto.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CollisionService {

    private final Logger log = LoggerFactory.getLogger(CollisionService.class);

    private final CurriculumSemesterService curriculumSemesterService;

    private final CurriculumSubjectService curriculumSubjectService;

    private final IdealPlanService idealPlanService;
    private final IdealPlanEntriesService idealPlanEntriesService;

    private final CollisionLevelOneService collisionLevelOneService;
    private final CollisionLevelTwoService collisionLevelTwoService;
    private final CollisionLevelThreeService collisionLevelThreeService;
    private final CollisionLevelFourService collisionLevelFourService;
    private final CollisionLevelFiveService collisionLevelFiveService;

    private final AppointmentService appointmentService;

    public CollisionService(CurriculumSemesterService curriculumSemesterService,
                            CurriculumSubjectService curriculumSubjectService,
                            IdealPlanService idealPlanService,
                            IdealPlanEntriesService idealPlanEntriesService,
                            CollisionLevelOneService collisionLevelOneService,
                            CollisionLevelTwoService collisionLevelTwoService,
                            CollisionLevelThreeService collisionLevelThreeService,
                            CollisionLevelFourService collisionLevelFourService,
                            CollisionLevelFiveService collisionLevelFiveService,
                            AppointmentService appointmentService) {
        this.curriculumSemesterService = curriculumSemesterService;
        this.curriculumSubjectService = curriculumSubjectService;
        this.idealPlanService = idealPlanService;
        this.idealPlanEntriesService = idealPlanEntriesService;
        this.collisionLevelOneService = collisionLevelOneService;
        this.collisionLevelTwoService = collisionLevelTwoService;
        this.collisionLevelThreeService = collisionLevelThreeService;
        this.collisionLevelFourService = collisionLevelFourService;
        this.collisionLevelFiveService = collisionLevelFiveService;
        this.appointmentService = appointmentService;
    }

    public void calculateCollisions() {
        // load ideal plan as map
        Map<Pair<String, SubjectType>, IdealPlanEntriesDTO> idealPlan = getIdealPlanMap(204, 2016, Semester.WS);

        // load CurriculumSubject
        CurriculumSemesterDTO one = curriculumSemesterService.findOne(204, 2017, Semester.SS);

        List<CurriculumSubjectDTO> all = curriculumSubjectService.findAll(one.getId());

        all.stream()
            //.filter(curriculumSubjectDTO -> curriculumSubjectDTO.getSubjectSubjectName().equals("Formale Grundlagen der Wirtschaftsinformatik") && curriculumSubjectDTO.getSubjectSubjectType().equals(SubjectType.VL))
            .forEach(curriculumSubjectDTO -> {
                // suche nach möglichen Kollisionsfächern
                Set<Optional<CurriculumSubjectDTO>> possibleCollisions = getPossibleCollisions(idealPlan, all, curriculumSubjectDTO);

                // Ausgabe der möglichen Kollisionsfächern
                System.out.println("##########################################################################");
                System.out.println(curriculumSubjectDTO.getSubjectSubjectName() + " " + curriculumSubjectDTO.getSubjectSubjectType());
                System.out.println("##########################################################################");
                possibleCollisions.forEach(curriculumSubjectDTO1 -> {
                    curriculumSubjectDTO1.ifPresent(curriculumSubjectDTO2 -> System.out.println(curriculumSubjectDTO2.getSubjectSubjectName() + " " + curriculumSubjectDTO2.getSubjectSubjectType()));
                });

                // Abrufen der LVAs des Ursprungsfachs
                Set<LvaDTO> lvas = curriculumSubjectDTO.getLvas();
                lvas.forEach(lvaDTO -> {
                    //Prüfen einer LVA des Ursprungsfachs gegen alle möglichen Kollisionsfächer
                    possibleCollisions.forEach(targetSubject -> {
                        if (targetSubject.isPresent()) {
                            CurriculumSubjectDTO curriculumSubjectDTO1 = targetSubject.get();
                            Set<LvaDTO> lvas1 = curriculumSubjectDTO1.getLvas();
                            lvas1.forEach(lvaDTO1 -> checkCollisionLva(lvaDTO, lvaDTO1));
                        }
                    });
                });

            });

    }

    private void checkCollisionLva(LvaDTO lvaDTO, LvaDTO lvaDTO1) {
        Set<AppointmentDTO> appointmentsSource = lvaDTO.getAppointments();
        Set<AppointmentDTO> appointmentsTarget = lvaDTO1.getAppointments();

        System.out.println(lvaDTO.getSubjectSubjectName() + " --> " + lvaDTO1.getSubjectSubjectName());
        appointmentsSource.forEach(appointmentDTO -> {
            Set<CollisionLevelFiveDTO> collect = appointmentsTarget
                .stream()
                .filter(appointmentDTO1 -> detectCollision(appointmentDTO, appointmentDTO1))
                .map(appointmentDTO1 -> {
                    CollisionLevelFiveDTO collisionLevelFiveDTO = new CollisionLevelFiveDTO();
                    collisionLevelFiveDTO.setSourceAppointmentId(appointmentDTO.getId());
                    collisionLevelFiveDTO.setTargetAppointmentId(appointmentDTO1.getId());
                    if (appointmentDTO.isIsExam() && appointmentDTO1.isIsExam())
                        collisionLevelFiveDTO.setExamCollision(1);
                    return collisionLevelFiveService.save(collisionLevelFiveDTO);
                }).collect(Collectors.toSet());
        });

    }

    private Set<Optional<CurriculumSubjectDTO>> getPossibleCollisions(Map<Pair<String, SubjectType>, IdealPlanEntriesDTO> idealPlan, List<CurriculumSubjectDTO> all, CurriculumSubjectDTO curriculumSubjectDTO) {
        //search for all possible subjects which can have a relevant collision
        IdealPlanEntriesDTO idealPlanEntriesDTO = idealPlan.get(new Pair<>(curriculumSubjectDTO.getSubjectSubjectName(), curriculumSubjectDTO.getSubjectSubjectType()));
        int winter = idealPlanEntriesDTO.getWinterSemesterDefault();
        int summer = idealPlanEntriesDTO.getSummerSemesterDefault();

        return idealPlan.entrySet().stream().filter(pairIdealPlanEntriesDTOEntry -> pairIdealPlanEntriesDTOEntry.getValue().getWinterSemesterDefault() == winter
            || pairIdealPlanEntriesDTOEntry.getValue().getSummerSemesterDefault() == summer)
            .filter(pairIdealPlanEntriesDTOEntry -> !pairIdealPlanEntriesDTOEntry.getKey().equals(curriculumSubjectDTO.getSubjectSubjectName())
                && !pairIdealPlanEntriesDTOEntry.getValue().getSubjectSubjectType().equals(curriculumSubjectDTO.getSubjectSubjectType()))
            .map(pairIdealPlanEntriesDTOEntry -> {
                return all.stream()
                    .filter(curriculumSubjectDTO1 -> {
                        return curriculumSubjectDTO1.getSubjectId() == pairIdealPlanEntriesDTOEntry.getValue().getSubjectId();
                    }).findFirst();
            }).collect(Collectors.toSet());
    }

    private Map<Pair<String, SubjectType>, IdealPlanEntriesDTO> getIdealPlanMap(Integer curId, Integer year, Semester semester) {
        IdealPlanDTO byCurriculum_curIdAndYearAndSemester = idealPlanService.findByCurriculum_CurIdAndYearAndSemester(curId, year, semester);

        log.debug("Request for Ideal Plan : {}", byCurriculum_curIdAndYearAndSemester);

        List<IdealPlanEntriesDTO> byIdealplan_id = idealPlanEntriesService.findByIdealplan_Id(byCurriculum_curIdAndYearAndSemester.getId());

        return byIdealplan_id.stream()
            .collect(Collectors.toMap(c -> {
                return new Pair<>(c.getSubjectSubjectName(), c.getSubjectSubjectType());

            }, c -> c));
    }


    private boolean detectCollision(AppointmentDTO a1, AppointmentDTO a2) {
        if (a1.getStartDateTime().equals(a2.getStartDateTime()) && a1.getEndDateTime().equals(a2.getEndDateTime())) return true;
        return ((a1.getStartDateTime().isAfter(a2.getStartDateTime()) && a1.getStartDateTime().isBefore(a2.getEndDateTime())) ||
            (a1.getEndDateTime().isAfter(a2.getStartDateTime()) && a1.getEndDateTime().isBefore(a2.getEndDateTime()))) ||
            ((a2.getStartDateTime().isAfter(a1.getStartDateTime()) && a2.getStartDateTime().isBefore(a1.getEndDateTime())) ||
                (a2.getEndDateTime().isAfter(a1.getStartDateTime()) && a2.getEndDateTime().isBefore(a1.getEndDateTime())));
    }

}
