package fact.it.assignmentservice.controller;

import fact.it.assignmentservice.dto.AssignmentRequest;
import fact.it.assignmentservice.dto.AssignmentResponse;
import fact.it.assignmentservice.exception.EntityCreationException;
import fact.it.assignmentservice.model.TaskAssignment;
import fact.it.assignmentservice.service.TaskAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class TaskAssignmentController {
    private final TaskAssignmentService assignmentService;

    @PostMapping("/add")
    public ResponseEntity<String> assignTaskToMember(@RequestBody AssignmentRequest request) {
        try {
            assignmentService.createAssignment(request);
            String successMessage = "The assignment is created successfully added.";
            return ResponseEntity.ok(successMessage);
        } catch (EntityCreationException e) {
            // Handle the exception and provide a specific error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating member: " + e.getMessage());
        }

    }

    @GetMapping("/get/all")
    public List<AssignmentResponse> getAllAssignments() {
        var allAssignments = assignmentService.getAllAssignments();
        System.out.println("All TaskAssignments: " + allAssignments);
        return allAssignments;
    }

    @GetMapping("/get/rNumberOrTaskCodeOrAssigmentCode")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByRNumberOrTaskCodeOrAssignmentCode(
            @RequestParam(required = false) String rNumber,
            @RequestParam(required = false) String taskCode,
            @RequestParam(required = false) String assignmentCode) {

        List<AssignmentResponse> assignmentResponses = assignmentService.getAssignmentsByRNumberOrTaskCode(
                rNumber, taskCode, assignmentCode);

        return ResponseEntity.ok(assignmentResponses);
    }
    @GetMapping("/getByRNumber/{rNumber}")
    public ResponseEntity<?> getAssignmentsByRNumber(@PathVariable String rNumber) {
        AssignmentResponse response = assignmentService.getAssignmentsByRNumber(rNumber);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment with RNumber " + rNumber + " not found");
        }
    }

    @GetMapping("/getByTaskCode/{taskCode}")
    public ResponseEntity<?> getAssignmentsByTaskCode(@PathVariable String taskCode) {
        AssignmentResponse response = assignmentService.getAssignmentsByTaskCode(taskCode);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment with TaskCode " + taskCode + " not found");
        }
    }

    @GetMapping("/getAssignmentByCode/{assignmentCode}")
    public ResponseEntity<?> getAssignmentByCode(@PathVariable String assignmentCode) {
        AssignmentResponse response = assignmentService.getAssignmentByCode(assignmentCode);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment with AssignmentCode " + assignmentCode + " not found");
        }
    }



    @PutMapping("/update/{assignmentCode}")
    public ResponseEntity<String> updateAssigment(
            @PathVariable String assignmentCode,
            @RequestBody AssignmentRequest assignmentRequest) {
        try {
            assignmentService.updateAssignment(assignmentCode, assignmentRequest);
            return ResponseEntity.ok("Assigment updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating assigment");
        }
    }

    @DeleteMapping("/delete/{assignmentCode}")
    public ResponseEntity<String> deleteAssignment(@PathVariable String assignmentCode) {
        try {
            assignmentService.deleteAssignment(assignmentCode);
            return new ResponseEntity<>("Assignment deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
