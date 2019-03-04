package ca.mcgill.ecse321.cooperator.controller;

import ca.mcgill.ecse321.cooperator.dto.*;
import ca.mcgill.ecse321.cooperator.model.*;
import ca.mcgill.ecse321.cooperator.services.CoopPositionService;
import ca.mcgill.ecse321.cooperator.services.EmployerService;
import ca.mcgill.ecse321.cooperator.services.RequiredDocumentService;
import ca.mcgill.ecse321.cooperator.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RequiredDocumentController {
    @Autowired
    RequiredDocumentService requiredDocumentService;

    @Autowired
    CoopPositionService coopPositionService;

    @Autowired
    EmployerService employerService;

    @Autowired
    UserEntityService userEntityService;

    // create report
    @PostMapping(value = {"/createReport", "/createReport/"})
    public ReportDto createReport(@RequestParam("name") String title,
                                  @RequestParam(name = "dueDate") @DateTimeFormat(pattern = "MM/dd/yyyy") Date dueDate,
                                  @RequestParam(name = "coopId") int cpId,
                                  @RequestParam(name = "reportType") ReportType type) throws IllegalArgumentException {
        CoopPosition cp = coopPositionService.getById(cpId);
        Report report = requiredDocumentService.createReport(title, dueDate, cp, type);
        return DtoConverters.convertToDto(report);
    }

    // create form
    @PostMapping(value = {"/createForm", "/createForm/"})
    public FormDto createForm(@RequestParam("name") String name,
                              @RequestParam(name = "dueDate") @DateTimeFormat(pattern = "MM/dd/yyyy") Date dueDate,
                              @RequestParam(name = "coopId") int cpId)
            throws IllegalArgumentException {
        CoopPosition cp = coopPositionService.getById(cpId);
        Form form = requiredDocumentService.createForm(name, dueDate, cp);
        return DtoConverters.convertToDto(form);
    }

    // create employer contract
    @PostMapping(value = {"/createEmployerContract", "/createEmployerContract/"})
    public EmployerContractDto createEmployerContract(@RequestParam(name = "name") String name,
                                                      @RequestParam(name = "dueDate") @DateTimeFormat(pattern = "MM/dd/yyyy") Date dueDate,
                                                      @RequestParam(name = "coopId") int cpId,
                                                      @RequestParam(name = "employerId") int eId)
            throws IllegalArgumentException {
        Employer e = employerService.getEmployerById(eId);
        CoopPosition cp = coopPositionService.getById(cpId);
        EmployerContract ec = requiredDocumentService.createEmployerContract(name, dueDate, cp, e);
        return DtoConverters.convertToDto(ec);
    }

    // view all documents submitted by student for specific coop
    @GetMapping(value = {"/requiredDocuments", "/requiredDocuments/"})
    public List<RequiredDocumentDto> getRequiredDocumentsByCoopPosition(
            @RequestParam(name = "coopId") int cpId) {
        CoopPosition cp = coopPositionService.getById(cpId);
        List<RequiredDocument> rdDto = requiredDocumentService.getRequiredDocumentByCoopPosition(cp);
        List<RequiredDocumentDto> rdDtos = new ArrayList<>();
        for (RequiredDocument r : rdDto) {
            RequiredDocumentDto rDto = DtoConverters.convertToDto(r);
            rdDtos.add(rDto);
        }
        return rdDtos;
    }

    // grade document by user
    @PostMapping(value = {"/gradeDocument", "/gradeDocument/"})
    public RequiredDocumentDto gradeDocument(@RequestParam(name = "documentId") int rdId,
                                 @RequestParam(name = "grade") String accepted,
                                 @RequestParam(name = "instructorEmail") String instructorEmail) throws IllegalArgumentException {
        UserEntity user = userEntityService.getUserEntityByEmail(instructorEmail);
        if (user == null || !(user instanceof TermInstructor))
            return null;
        else 
        	requiredDocumentService.gradeDocument(rdId,accepted);
        RequiredDocument rdoc = requiredDocumentService.getRequiredDocumentById(rdId);
        return DtoConverters.convertToDto(rdoc);
    }

    // viewing graded document
    @GetMapping(value = {"/grade", "/grade/"})
    public RequiredDocumentDto viewGrade(@RequestParam(name = "documentId") int rdId) throws IllegalArgumentException {
        RequiredDocument rd = requiredDocumentService.getRequiredDocumentById(rdId);
        if (rd.getAccepted() == null)
            return null;
        else rd.getAccepted();
        return DtoConverters.convertToDto(rd);
    }
}
