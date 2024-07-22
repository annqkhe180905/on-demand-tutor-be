package online.ondemandtutor.be.service;

import online.ondemandtutor.be.entity.SearchTutor;
import online.ondemandtutor.be.model.SearchTutorRequest;
import online.ondemandtutor.be.repository.SearchTutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTutorService {

    @Autowired
    SearchTutorRepository searchTutorRepository;

    public List<SearchTutor> searchTutorsByName(SearchTutorRequest searchTutorRequest) {
        return searchTutorRepository.findByNameContainingIgnoreCase(searchTutorRequest.getName());
    }

    public List<SearchTutor> searchTutorsBySubject(SearchTutorRequest searchTutorRequest) {
        return searchTutorRepository.findBySubjectContainingIgnoreCase(searchTutorRequest.getSubject());
    }

    public List<SearchTutor> searchTutorsByLocation(SearchTutorRequest searchTutorRequest) {
        return searchTutorRepository.findByLocationContainingIgnoreCase(searchTutorRequest.getLocation());
    }
}
