package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.model.SubjectRegisterRequest;
import online.ondemandtutor.be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private EducationLevelRepository educationLevelRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SubjectRegisterRepository subjectRegisterRepository;
    @Autowired
    private WeekDayRepository weekDayRepository;

    @Autowired
    private TeachingSlotRepository teachingSlotRepository;
    @Autowired
    private GradeRepository gradeRepository;
//    @Autowired
//    private GradeRepository gradeRepository;
//    @Autowired
//    private WeekDayRepository weekDayRepository;

//    @Autowired
//    private GradeRepository gradeRepository;
//    @Autowired
//    private WeekDayRepository weekDayRepository;


    //CRUD: read all
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    //CRUD: read by name
    public List<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    //CRUD: create
//    public Subject createSubject(SubjectRequest subjectRequest) {
//        Subject subject = new Subject();
//
//        //tìm ID nhận vào từ SubjectRequest
//        Category category = categoryRepository.findById(subjectRequest.getCategoryId());
//        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(subjectRequest.getScheduleId());
//
//        subject.setName(subjectRequest.getName());
////        subject.setGrade(subjectRequest.getGrade());
//        subject.setCategory(category);
//
//        subject.setTutorSchedules(schedule);
//
//        Subject newSubject = subjectRepository.save(subject);
//        return newSubject;
//    }
//
//    //CRUD: update
//    public Subject updateSubject(SubjectRequest subjectRequest, long id) {
//        Subject subject = subjectRepository.findById(id);
//        Category category = categoryRepository.findById(subjectRequest.getCategoryId());
//        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(subjectRequest.getScheduleId());
//
//        subject.setName(subjectRequest.getName());
////        subject.setGrade(subjectRequest.getGrade());
//        subject.setCategory(category);
//
//        subject.setTutorSchedules(schedule);
//
//        Subject newSubject = subjectRepository.save(subject);
//        return newSubject;
//    }

    //CRUD: delete
//    public Subject deleteSubject(long id) {
//        Subject subject = subjectRepository.findById(id);
//        if (subject != null) {
//            subject.setDeleted(true);
//            Subject newSubject = subjectRepository.save(subject);
//            return newSubject;
//        }
//        else {
//            return null;
//        }
//    }

    /*
    * Long educationLevelId;        tick
    List<Long> locationIds;         tick
    List<Long> subjectIds;          tick
    List<Long> gradeIds;            tick
    String TutorVideoUrl;           tick
    List<Long> weekDayIds;
    List<Long> teachingSlotIds;
    Long accountId;                 tick
    String brief;
    * */
    //form de TUTOR dang ky mon hoc voi MODERATOR

    public SubjectRegister tutorRegisterSubject(SubjectRegisterRequest request){
        Account account = authenticationService.getCurrentAccount();
        SubjectRegister subjectRegister = new SubjectRegister();
        EducationLevel level = educationLevelRepository.findEducationLevelById(request.getEducationLevelId());


        ArrayList<Location> locations = new ArrayList<>();
        for(Long findId: request.getLocationIds()){
            Location location = locationRepository.findLocationById(findId);
            locations.add(location);
        }

        ArrayList<Subject> subjects = new ArrayList<>();
        for(Long findId: request.getSubjectIds()){
            Subject subject = subjectRepository.findSubjectById(findId);
            subjects.add(subject);
        }

        ArrayList<Grade> grades = new ArrayList<>();
        for(Long findId: request.getGradeIds()){
            Grade grade = gradeRepository.findGradeById(findId);
            grades.add(grade);
        }


        TutorVideo tutorVideo = new TutorVideo();
        tutorVideo.setUrl(request.getTutorVideoUrl());

        // Handle WeekDays
        ArrayList<WeekDay> weekDays = new ArrayList<>();
        for (Long findId : request.getWeekDayIds()) {
            WeekDay day = weekDayRepository.findWeekDayById(findId);
            day.setSubjectRegister(subjectRegister); // Link the WeekDay to SubjectRegister
            weekDays.add(day);
        }

        // Handle TeachingSlots
        ArrayList<TeachingSlot> teachingSlots = new ArrayList<>();
        for (Long findId : request.getTeachingSlotIds()) {
            TeachingSlot slot = teachingSlotRepository.findTeachingSlotById(findId);
            WeekDay weekDay = slot.getWeekDay(); // Assume TeachingSlot already has a WeekDay
            if (weekDay != null && weekDay.getSubjectRegister() == null) {
                weekDay.setSubjectRegister(subjectRegister); // Link the WeekDay to SubjectRegister if not already linked
                weekDays.add(weekDay);
            }
            teachingSlots.add(slot);
        }

        subjectRegister.setEducationLevelId(level);
        subjectRegister.setLocationIds(locations);
        subjectRegister.setSubjectIds(subjects);
        subjectRegister.setTutorVideoUrl(tutorVideo);
        subjectRegister.setWeekDayIds(weekDays);
        subjectRegister.setGradeIds((grades));
        subjectRegister.setAccountId(account);
        subjectRegister.setBrief(request.getBrief());

        return subjectRegisterRepository.save(subjectRegister);

    }

}
