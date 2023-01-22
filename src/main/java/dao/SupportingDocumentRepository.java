package dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.SupportingDocument;

@Repository
public interface SupportingDocumentRepository extends JpaRepository<SupportingDocument, String>{

	List<SupportingDocument> findAllByApplication(Application application);

}