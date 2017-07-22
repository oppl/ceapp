package at.meroff.itproject.service;

import at.meroff.itproject.domain.*;
import at.meroff.itproject.domain.enumeration.Semester;
import at.meroff.itproject.domain.enumeration.SubjectType;
import at.meroff.itproject.repository.*;
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
public class CollisionService {

    static final double BASE_VALUE_APPOINTMENT_COLLISION = 100.0;
    static final int MULTIPLIER_FOR_COLLISION = 3;

    private final Logger log = LoggerFactory.getLogger(CollisionService.class);

    private final CurriculumSemesterRepository curriculumSemesterRepository;

    private final CurriculumSubjectRepository curriculumSubjectRepository;

    private final CurriculumRepository curriculumRepository;

    private final IdealPlanRepository idealPlanRepository;
    private final IdealPlanEntriesRepository idealPlanEntriesRepository;

    private final CollisionLevelOneRepository collisionLevelOneRepository;
    private final CollisionLevelTwoRepository collisionLevelTwoRepository;
    private final CollisionLevelThreeRepository collisionLevelThreeRepository;
    private final CollisionLevelFourRepository collisionLevelFourRepository;
    private final CollisionLevelFiveRepository collisionLevelFiveRepository;

    private final AppointmentRepository appointmentRepository;

    public CollisionService(CurriculumSemesterRepository curriculumSemesterRepository,
                            CurriculumSubjectRepository curriculumSubjectRepository,
                            CurriculumRepository curriculumRepository,
                            IdealPlanRepository idealPlanRepository,
                            IdealPlanEntriesRepository idealPlanEntriesRepository,
                            CollisionLevelOneRepository collisionLevelOneRepository,
                            CollisionLevelTwoRepository collisionLevelTwoRepository,
                            CollisionLevelThreeRepository collisionLevelThreeRepository,
                            CollisionLevelFourRepository collisionLevelFourRepository,
                            CollisionLevelFiveRepository collisionLevelFiveRepository,
                            AppointmentRepository appointmentRepository) {
        this.curriculumSemesterRepository = curriculumSemesterRepository;
        this.curriculumSubjectRepository = curriculumSubjectRepository;
        this.curriculumRepository = curriculumRepository;
        this.idealPlanRepository = idealPlanRepository;
        this.idealPlanEntriesRepository = idealPlanEntriesRepository;
        this.collisionLevelOneRepository = collisionLevelOneRepository;
        this.collisionLevelTwoRepository = collisionLevelTwoRepository;
        this.collisionLevelThreeRepository = collisionLevelThreeRepository;
        this.collisionLevelFourRepository = collisionLevelFourRepository;
        this.collisionLevelFiveRepository = collisionLevelFiveRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Diese Methode berechnet für ein gegebenes Curriculum für ein Semster die Kollisionen
     * in Abhängigkeit von einem spezifischen idealtypischen Studienverlauf
     * @param curId
     * @param idealPlanYear
     * @param idealPlanSemester
     * @param year
     * @param semester
     */
    // TODO Idealtypischen Studienplan (Objekt) als Parameter verwenden
    // TODO Curriculum Objekt als Parameter verwenden
    public void calculateCollisions(Integer curId, Integer idealPlanYear, Semester idealPlanSemester, Integer year, Semester semester) {

        // load CurriculumSemester
        CurriculumSemester currSemester = curriculumSemesterRepository.findByCurriculum_CurIdAndYearAndSemester(curId, year, semester);

        // load idealPlan
        IdealPlan idealPlan = idealPlanRepository.findByCurriculum_CurIdAndYearAndSemester(curId, idealPlanYear, idealPlanSemester);

        calculateCollisions(currSemester, idealPlan);

    }

    public void calculateCollisions(CurriculumSemester cSem, IdealPlan idealPlan) {
        // load ideal plan as map
        Map<Pair<String, SubjectType>, IdealPlanEntries> idealPlanMap = getIdealPlanMap(idealPlan);

        // find institutes linked with the curriculum
        List<Long> curriculumInstitutes = getCurriculumInstitutes(idealPlan);

        // find all CurriculumSubjects for a given CurriculumSemester
        List<CurriculumSubject> csAll = curriculumSubjectRepository.findByCurriculumSemester_Id(cSem.getId());

        // filtering for testing only!!!
        /*csAll = csAll
            .stream()
            //.filter(cs -> cs.getSubject().getSubjectName().equals("Formale Grundlagen der Wirtschaftsinformatik"))
            .collect(Collectors.toList());*/

        Set<CollisionLevelOne> collect = csAll
            .stream()
            .map(c -> createLevelOne(csAll, c, idealPlanMap, curriculumInstitutes))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        System.out.println(collect);
        collisionLevelOneRepository.save(collect);
        collect.forEach(collisionLevelOne -> {
            collisionLevelTwoRepository.save(collisionLevelOne.getCollisionLevelTwos())
                .stream()
                .forEach(collisionLevelTwo -> {
                    collisionLevelThreeRepository.save(collisionLevelTwo.getCollisionLevelThrees())
                        .stream()
                        .forEach(collisionLevelThree -> {
                            collisionLevelFourRepository.save(collisionLevelThree.getCollisionLevelFours())
                                .stream()
                                .forEach(collisionLevelFour -> {
                                    collisionLevelFiveRepository.save(collisionLevelFour.getCollisionLevelFives());
                                });
                        });

            });

        });

    }

    private List<Long> getCurriculumInstitutes(IdealPlan idealPlan) {
        // load Curriculum
        Curriculum curriculumDTO = curriculumRepository.findOne(idealPlan.getCurriculum().getId());

        // load a list of linked Institutes for a given Curriculum
        return curriculumDTO
            .getInstitutes()
            .stream()
            .map(institute -> institute.getId())
            .collect(Collectors.toList());
    }

    private CollisionLevelOne createLevelOne(List<CurriculumSubject> csAll,
                                CurriculumSubject cs,
                                Map<Pair<String, SubjectType>, IdealPlanEntries> idealPlanMap,
                                List<Long> curriculumInstitutes) {
        Set<CurriculumSubject> collisionSubjects = findCollisionSubjects(cs, csAll, idealPlanMap);

        Set<Lva> sourceLvas = cs.getLvas();

        Set<CollisionLevelTwo> levelTwo = sourceLvas
            .stream()
            .map(sourceLva -> createLevelTwo(sourceLva, collisionSubjects, idealPlanMap, curriculumInstitutes))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        if (levelTwo.size() > 0) {
            CollisionLevelOne levelOne = new CollisionLevelOne();
            levelOne.setExamCollision(levelTwo.stream().mapToInt(l -> l.getExamCollision()).sum());
            levelTwo.forEach(l -> l.setCollisionLevelOne(levelOne));
            levelOne.setCollisionLevelTwos(levelTwo);
            return levelOne;
        }
        return null;
    }

    private CollisionLevelTwo createLevelTwo(Lva sourceLva, Set<CurriculumSubject> collisionSubjects, Map<Pair<String, SubjectType>, IdealPlanEntries> idealPlanMap, List<Long> curriculumInstitutes) {

        Set<CollisionLevelThree> levelThree = collisionSubjects.stream()
            .map(curriculumSubject -> createLevelTree(sourceLva, curriculumSubject, curriculumInstitutes))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        if (levelThree.size() > 0) {
            CollisionLevelTwo levelTwo = new CollisionLevelTwo();
            levelTwo.setExamCollision(levelThree.stream().mapToInt(l -> l.getExamCollision()).sum());
            levelTwo.setLva(sourceLva);
            levelThree.forEach(l -> l.setCollisionLevelTwo(levelTwo));
            levelTwo.setCollisionLevelThrees(levelThree);
            return levelTwo;
        }

        return null;
    }

    private CollisionLevelThree createLevelTree(Lva sourceLva, CurriculumSubject collisionSubject, List<Long> institutes) {
        Set<Lva> targetLvas = collisionSubject.getLvas();

        Set<CollisionLevelFour> levelFours = targetLvas
            .stream()
            .map(l -> createLevelFour(sourceLva, l, institutes))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        if (levelFours.size() > 0) {
            CollisionLevelThree levelThree = new CollisionLevelThree();
            levelThree.setExamCollision(levelFours.stream().mapToInt(l -> l.getExamCollision()).sum());
            levelThree.setCurriculumSubject(collisionSubject);
            levelFours.forEach(l -> l.setCollisionLevelThree(levelThree));
            levelThree.setCollisionLevelFours(levelFours);
            //collisionLevelFourRepository.save(levelFours);
            return levelThree;
        }
        return null;
    }

    private CollisionLevelFour createLevelFour(Lva source, Lva target, List<Long> institutes) {
        Set<Appointment> sourceAppointments = source.getAppointments();
        Set<Appointment> targetAppointments = target.getAppointments();

        Set<CollisionLevelFive> levelFives = sourceAppointments
            .stream()
            .map(s -> {
                return targetAppointments
                    .stream()
                    .filter(t -> detectCollision(s, t))
                    .map(t -> createLevelFive(s, t, institutes))
                    .collect(Collectors.toSet());
            })
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        if (levelFives.size() > 0) {
            CollisionLevelFour levelFour = new CollisionLevelFour();
            // TODO korrigieren
            levelFour.setLva(target);
            levelFour.setInstituteCollision(0);
            levelFour.setCurriculumCollision(0);
            levelFour.setExamCollision(levelFives.stream().mapToInt(l -> l.getExamCollision()).sum());
            levelFives.forEach(l -> l.setCollisionLevelFour(levelFour));
            levelFour.setCollisionLevelFives(levelFives);
            //collisionLevelFiveRepository.save(levelFives);
            return levelFour;
        }
        return null;
    }

    private CollisionLevelFive createLevelFive(Appointment source, Appointment target, List<Long> institutes) {
        if (detectCollision(source, target)) {
            CollisionLevelFive levelFive = new CollisionLevelFive();

            levelFive.setSourceAppointment(source);
            levelFive.setTargetAppointment(target);

            if (source.isIsExam() && target.isIsExam()){
                levelFive.setExamCollision(1);
                levelFive.setCollisionValue(300.0);
            } else {
                levelFive.setExamCollision(0);
                levelFive.setCollisionValue(100.0);
            }

            return levelFive;
        }
        return null;
    }

    private Set<CurriculumSubject> findCollisionSubjects(CurriculumSubject cs, List<CurriculumSubject> csAll, Map<Pair<String, SubjectType>, IdealPlanEntries> idealPlanMap) {
        //search for all possible subjects which can have a relevant collision
        IdealPlanEntries idealPlanEntriesDTO = idealPlanMap.get(new Pair<>(cs.getSubject().getSubjectName(), cs.getSubject().getSubjectType()));
        int winter = idealPlanEntriesDTO.getWinterSemesterDefault();
        int summer = idealPlanEntriesDTO.getSummerSemesterDefault();

        Set<Map.Entry<Pair<String, SubjectType>, IdealPlanEntries>> collect = idealPlanMap.entrySet().stream().filter(pairIdealPlanEntriesDTOEntry -> pairIdealPlanEntriesDTOEntry.getValue().getWinterSemesterDefault() == winter
            || pairIdealPlanEntriesDTOEntry.getValue().getSummerSemesterDefault() == summer).collect(Collectors.toSet());
        return collect.stream()
            .filter(pairIdealPlanEntriesDTOEntry -> !pairIdealPlanEntriesDTOEntry.getKey().equals(cs.getSubject().getSubjectName())
                && !pairIdealPlanEntriesDTOEntry.getValue().getSubject().getSubjectType().equals(cs.getSubject().getSubjectType()))
            .map(pairIdealPlanEntriesDTOEntry -> {
                return csAll.stream()
                    .filter(curriculumSubjectDTO1 -> {
                        return curriculumSubjectDTO1.getSubject().getId() == pairIdealPlanEntriesDTOEntry.getValue().getSubject().getId();
                    }).findFirst();
            }).filter(curriculumSubject -> curriculumSubject.isPresent()).map(curriculumSubject -> curriculumSubject.get()).collect(Collectors.toSet());


    }


    /**
     * Diese Methode liest einen spezifischen idealtypischen Studienverlauf aus und liefert eine Map der
     * enthaltenen Fächer zurück.
     * @param curId
     * @param year
     * @param semester
     * @return
     */
    private Map<Pair<String, SubjectType>, IdealPlanEntries> getIdealPlanMap(IdealPlan idealPlan) {

        // abrufen der mit dem idealtypischen Studienverlauf verknüpften Einträge
        List<IdealPlanEntries> entries = idealPlanEntriesRepository.findByIdealplan_Id(idealPlan.getId());

        // Map für Fäecher und zugeordneten
        return entries.stream()
            .collect(Collectors.toMap(
                entry -> {return new Pair<>(entry.getSubject().getSubjectName(), entry.getSubject().getSubjectType());},
                entry -> entry)
            );
    }

    private boolean detectCollision(Appointment a1, Appointment a2) {
        if (a1.getStartDateTime().equals(a2.getStartDateTime()) && a1.getEndDateTime().equals(a2.getEndDateTime())) return true;
        return ((a1.getStartDateTime().isAfter(a2.getStartDateTime()) && a1.getStartDateTime().isBefore(a2.getEndDateTime())) ||
            (a1.getEndDateTime().isAfter(a2.getStartDateTime()) && a1.getEndDateTime().isBefore(a2.getEndDateTime()))) ||
            ((a2.getStartDateTime().isAfter(a1.getStartDateTime()) && a2.getStartDateTime().isBefore(a1.getEndDateTime())) ||
                (a2.getEndDateTime().isAfter(a1.getStartDateTime()) && a2.getEndDateTime().isBefore(a1.getEndDateTime())));
    }

}
