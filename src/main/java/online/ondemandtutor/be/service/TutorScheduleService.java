package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.entity.Subject;
import online.ondemandtutor.be.entity.TutorSchedule;
import online.ondemandtutor.be.exception.BadRequestException;
import online.ondemandtutor.be.model.TutorScheduleRequest;
import online.ondemandtutor.be.repository.AuthenticationRepository;
import online.ondemandtutor.be.repository.SubjectRepository;
import online.ondemandtutor.be.repository.TutorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorScheduleService {
    @Autowired
    TutorScheduleRepository tutorScheduleRepository;
    @Autowired
    private AuthenticationRepository authenticationRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public TutorSchedule addSchedule(TutorScheduleRequest tutorScheduleRequest){
        TutorSchedule tutorSchedule = new TutorSchedule();
        tutorSchedule.setId(tutorScheduleRequest.getId());

        Account account = authenticationService.getCurrentAccount();
        tutorSchedule.setAccount(account);
        Subject subject = subjectRepository.findById(tutorScheduleRequest.getSubject_id());
        tutorSchedule.setSubject(subject);
        //thieu booking_id
        tutorSchedule.setWeekDay(tutorScheduleRequest.getWeekDay());
        tutorSchedule.setTeachingTime(tutorScheduleRequest.getTeachingTime());
        return tutorScheduleRepository.save(tutorSchedule);
    }

    public TutorSchedule updateSchedule(TutorScheduleRequest tutorScheduleRequest, long id){
        TutorSchedule tutorSchedule = tutorScheduleRepository.findTutorScheduleById(id);
        if(tutorSchedule == null){
            throw new BadRequestException("Tutor schedule is not found");
        }
        //thieu booking_id
        Account account = authenticationService.getCurrentAccount();
        tutorSchedule.setAccount(account);
        Subject subject = subjectRepository.findById(tutorScheduleRequest.getSubject_id());
        tutorSchedule.setSubject(subject);

        tutorSchedule.setWeekDay(tutorScheduleRequest.getWeekDay());
        tutorSchedule.setTeachingTime(tutorScheduleRequest.getTeachingTime());
        return tutorScheduleRepository.save(tutorSchedule);
    }
}
