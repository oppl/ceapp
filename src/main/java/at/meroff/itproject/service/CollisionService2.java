package at.meroff.itproject.service;

import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.service.dto.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CollisionService2 {

    static final double BASE_VALUE_APPOINTMENT_COLLISION = 100.0;
    static final int MULTIPLIER_FOR_COLLISION = 3;

    private final Logger log = LoggerFactory.getLogger(CollisionService2.class);

    private final CurriculumSemesterService curriculumSemesterService;

    private final CurriculumSubjectService curriculumSubjectService;

    private final CurriculumService curriculumService;

    private final IdealPlanService idealPlanService;
    private final IdealPlanEntriesService idealPlanEntriesService;

    private final CollisionLevelOneService collisionLevelOneService;
    private final CollisionLevelTwoService collisionLevelTwoService;
    private final CollisionLevelThreeService collisionLevelThreeService;
    private final CollisionLevelFourService collisionLevelFourService;
    private final CollisionLevelFiveService collisionLevelFiveService;

    private final AppointmentService appointmentService;

    public CollisionService2(CurriculumSemesterService curriculumSemesterService,
                             CurriculumSubjectService curriculumSubjectService,
                             IdealPlanService idealPlanService,
                             IdealPlanEntriesService idealPlanEntriesService,
                             CollisionLevelOneService collisionLevelOneService,
                             CollisionLevelTwoService collisionLevelTwoService,
                             CollisionLevelThreeService collisionLevelThreeService,
                             CollisionLevelFourService collisionLevelFourService,
                             CollisionLevelFiveService collisionLevelFiveService,
                             AppointmentService appointmentService,
                             CurriculumService curriculumService) {
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
        this.curriculumService = curriculumService;
    }

    /**
     * Diese Methode berechnet für ein gegebenes Curriculum für ein Semster die Kollisionen
     * in Abhängigkeit von einem spezifischen idealtypischen Studienverlauf
     * @param curId
     * @param yearIdeal
     * @param semesterIdeal
     * @param year
     * @param semester
     */
    // TODO Idealtypischen Studienplan (Objekt) als Parameter verwenden
    // TODO Curriculum Objekt als Parameter verwenden
    public void calculateCollisions(Integer curId, Integer yearIdeal, Semester semesterIdeal, Integer year, Semester semester) {
        // load ideal plan as map
        Map<Pair<String, SubjectType>, IdealPlanEntriesDTO> idealPlan = getIdealPlanMap(curId, yearIdeal, semesterIdeal);

        // load CurriculumSemester
        CurriculumSemesterDTO currSemester = curriculumSemesterService.findOne(curId, year, semester);

        // load Curriculum
        CurriculumDTO curriculumDTO = curriculumService.findByCurId(curId);

        // load a list of linked Institutes for a given Curriculum
        List<Long> instituteIds = curriculumDTO.getInstitutes().stream().map(InstituteDTO::getId).collect(Collectors.toList());

        // find all CurriculumSubjects for a given CurriculumSemester
        List<CurriculumSubjectDTO> all = curriculumSubjectService.findAll(currSemester.getId());


        all.stream()
            .filter(curriculumSubjectDTO -> curriculumSubjectDTO.getSubjectSubjectName().equals("Formale Grundlagen der Wirtschaftsinformatik"))
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
                Set<CollisionLevelTwoDTO> collect3 = lvas.stream().map(lvaDTO -> {
                    //Prüfen einer LVA des Ursprungsfachs gegen alle möglichen Kollisionsfächer
                    Set<CollisionLevelThreeDTO> collect1 = possibleCollisions.stream().map(targetSubject -> {
                        if (targetSubject.isPresent()) {
                            // Zusammenfassung auf Fach Ziel
                            CurriculumSubjectDTO curriculumSubjectDTO1 = targetSubject.get();
                            Set<LvaDTO> lvas1 = curriculumSubjectDTO1.getLvas();
                            Set<CollisionLevelFourDTO> collect = lvas1.stream()
                                .map(lvaDTO1 -> checkCollisionLva(lvaDTO, lvaDTO1, instituteIds))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet());
                            CollisionLevelThreeDTO collisionLevelThreeDTO = new CollisionLevelThreeDTO();

                            if (collect.size() > 0) {
                                collisionLevelThreeDTO.setCurriculumSubjectId(targetSubject.get().getId());
                                collisionLevelThreeDTO.setExamCollision(collect.stream().mapToInt(CollisionLevelFourDTO::getExamCollision).sum());
                                collisionLevelThreeDTO = collisionLevelThreeService.save(collisionLevelThreeDTO);
                                CollisionLevelThreeDTO finalCollisionLevelThreeDTO = collisionLevelThreeDTO;
                                collect.forEach(collisionLevelFourDTO -> {
                                    collisionLevelFourDTO.setCollisionLevelThreeId(finalCollisionLevelThreeDTO.getId());
                                    collisionLevelFourService.save(collisionLevelFourDTO);
                                });
                                return collisionLevelThreeDTO;
                            }
                            return null;
                        }
                        return null;
                    }).filter(Objects::nonNull)
                        .collect(Collectors.toSet());

                    CollisionLevelTwoDTO collisionLevelTwoDTO = new CollisionLevelTwoDTO();

                    if (collect1.size() > 0) {
                        collisionLevelTwoDTO.setLvaId(lvaDTO.getId());
                        collisionLevelTwoDTO.setExamCollision(collect1.stream().mapToInt(CollisionLevelThreeDTO::getExamCollision).sum());
                        collisionLevelTwoDTO = collisionLevelTwoService.save(collisionLevelTwoDTO);
                        CollisionLevelTwoDTO finalCollisionLevelTwoDTO = collisionLevelTwoDTO;
                        collect1.forEach(collisionLevelThreeDTO -> {
                            collisionLevelThreeDTO.setCollisionLevelTwoId(finalCollisionLevelTwoDTO.getId());
                            collisionLevelThreeService.save(collisionLevelThreeDTO);
                        });
                        return collisionLevelTwoDTO;
                    }
                    return null;

                }).filter(Objects::nonNull).collect(Collectors.toSet());

                CollisionLevelOneDTO collisionLevelOneDTO = new CollisionLevelOneDTO();
                if (collect3.size() > 0) {
                    collisionLevelOneDTO.setCurriculumSubjectId(curriculumSubjectDTO.getId());
                    collisionLevelOneDTO.setExamCollision(collect3.stream().mapToInt(value -> value.getExamCollision()).sum());
                    collisionLevelOneDTO = collisionLevelOneService.save(collisionLevelOneDTO);
                    CollisionLevelOneDTO finalCollisionLevelOneDTO = collisionLevelOneDTO;
                    collect3.forEach(collisionLevelTwoDTO -> {
                        collisionLevelTwoDTO.setCollisionLevelOneId(finalCollisionLevelOneDTO.getId());
                        collisionLevelTwoService.save(collisionLevelTwoDTO);
                    });
                }

            });

    }

    private CollisionLevelFourDTO checkCollisionLva(LvaDTO lvaDTO, LvaDTO lvaDTO1, List<Long> instituteIds) {
        Set<AppointmentDTO> appointmentsSource = lvaDTO.getAppointments();
        Set<AppointmentDTO> appointmentsTarget = lvaDTO1.getAppointments();

        System.out.println(lvaDTO.getSubjectSubjectName() + " --> " + lvaDTO1.getSubjectSubjectName());
        Set<CollisionLevelFiveDTO> collect = appointmentsSource.stream().map(appointmentDTO -> {
            return appointmentsTarget
                .stream()
                .filter(appointmentDTO1 -> detectCollision(appointmentDTO, appointmentDTO1))
                .map(appointmentDTO1 -> {
                    CollisionLevelFiveDTO collisionLevelFiveDTO = new CollisionLevelFiveDTO();
                    collisionLevelFiveDTO.setSourceAppointmentId(appointmentDTO.getId());
                    collisionLevelFiveDTO.setTargetAppointmentId(appointmentDTO1.getId());
                    if (appointmentDTO.isIsExam() && appointmentDTO1.isIsExam()) {
                        collisionLevelFiveDTO.setExamCollision(1);
                        collisionLevelFiveDTO.setCollisionValue(BASE_VALUE_APPOINTMENT_COLLISION * MULTIPLIER_FOR_COLLISION);
                    } else {
                        collisionLevelFiveDTO.setExamCollision(0);
                        collisionLevelFiveDTO.setCollisionValue(BASE_VALUE_APPOINTMENT_COLLISION);
                    }
                    return collisionLevelFiveDTO;
                }).collect(Collectors.toSet());
        }).flatMap(Collection::stream).filter(Objects::nonNull).collect(Collectors.toSet());

        CollisionLevelFourDTO collisionLevelFourDTO = new CollisionLevelFourDTO();

        if (collect.size() > 0) {

            collisionLevelFourDTO.setLvaId(lvaDTO1.getId());
            if (lvaDTO.getInstituteId() == lvaDTO1.getInstituteId()) {
                collisionLevelFourDTO.setInstituteCollision(1);
            } else {
                collisionLevelFourDTO.setInstituteCollision(0);
            }

            if (instituteIds.contains(lvaDTO.getInstituteId()) && instituteIds.contains(lvaDTO1.getInstituteId())) {
                collisionLevelFourDTO.setCurriculumCollision(1);
            } else {
                collisionLevelFourDTO.setCurriculumCollision(0);
            }

            collisionLevelFourDTO = collisionLevelFourService.save(collisionLevelFourDTO);

            collisionLevelFourDTO.setExamCollision(collect.stream().mapToInt(collisionLevelFiveDTO -> collisionLevelFiveDTO.getExamCollision()).sum());

            CollisionLevelFourDTO finalCollisionLevelFourDTO = collisionLevelFourDTO;

            collect.forEach(collisionLevelFiveDTO -> {
                collisionLevelFiveDTO.setCollisionLevelFourId(finalCollisionLevelFourDTO.getId());
                collisionLevelFiveService.save(collisionLevelFiveDTO);
            });
            return collisionLevelFourDTO;
        }

        return null;



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
