package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.*;
import online.ondemandtutor.be.model.SubjectRegisterRequest;
import online.ondemandtutor.be.model.SubjectRequest;
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
    private TutorScheduleRepository tutorScheduleRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private EducationLevelRepository educationLevelRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private TutorVideoRepository tutorVideoRepository;

    //CRUD: read all
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    //CRUD: read by name
    public List<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    //CRUD: create
    public Subject createSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();

        //tìm ID nhận vào từ SubjectRequest
        Category category = categoryRepository.findById(subjectRequest.getCategoryId());
        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(subjectRequest.getScheduleId());

        subject.setName(subjectRequest.getName());
//        subject.setGrade(subjectRequest.getGrade());
        subject.setCategory(category);

        subject.setTutorSchedules(schedule);

        Subject newSubject = subjectRepository.save(subject);
        return newSubject;
    }

    //CRUD: update
    public Subject updateSubject(SubjectRequest subjectRequest, long id) {
        Subject subject = subjectRepository.findById(id);
        Category category = categoryRepository.findById(subjectRequest.getCategoryId());
        List<TutorSchedule> schedule = tutorScheduleRepository.findAllBySubjectId(subjectRequest.getScheduleId());

        subject.setName(subjectRequest.getName());
//        subject.setGrade(subjectRequest.getGrade());
        subject.setCategory(category);

        subject.setTutorSchedules(schedule);

        Subject newSubject = subjectRepository.save(subject);
        return newSubject;
    }

    //CRUD: delete
    public Subject deleteSubject(long id) {
        Subject subject = subjectRepository.findById(id);
        if (subject != null) {
            subject.setDeleted(true);
            Subject newSubject = subjectRepository.save(subject);
            return newSubject;
        }
        else {
            return null;
        }
    }

    public void tutorRegisterSubject(SubjectRegisterRequest request){
        Account account = authenticationService.getCurrentAccount();

        EducationLevel level = educationLevelRepository.findById(request.getEducationLevelId()).get();

        ArrayList<Location> locations = new ArrayList<>();
        for(Long findId: request.getLocationIds()){
            Location location = locationRepository.findById(findId).get();
            locations.add(location);
        }

        ArrayList<Subject> subjects = new ArrayList<>();
        for(Long findId: request.getSubjectIds()){
            Subject subject = subjectRepository.findById(findId).get();
            subjects.add(subject);
        }

        ArrayList<Grade> grades = new ArrayList<>();
        for(Long findId: request.getGradeIds()){
            Grade grade = gradeRepository.findById(findId);
            grades.add(grade);
        }

        TutorVideo tutorVideo = new TutorVideo();
        tutorVideo.setUrl(request.getTutorVideoUrl());

        String brief = request.getBrief();

    }

}
