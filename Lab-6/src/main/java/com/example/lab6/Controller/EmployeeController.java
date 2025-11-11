package com.example.lab6.Controller;
import com.example.lab6.API.ApiResponse;
import com.example.lab6.Model.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.time.LocalDate;
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ArrayList<Employee> getEmployees(){
        return employees ;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody @Valid Employee employee, Errors errors){
        if(errors.hasErrors()){
            String messsage=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(messsage));
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("The Employee Is Added Successfully"));
    }

    @PostMapping("/update/{index}")
    public ResponseEntity<?> updateEmployee(@PathVariable int index, @RequestBody @Valid Employee employee,Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        employees.set(index,employee);
        return ResponseEntity.status(200).body(new ApiResponse("The Employee Is Updated Successfully"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index,Employee employee){

        if(index<0 ||index>=employees.size()){
            return  ResponseEntity.status(400).body(new ApiResponse("The Employee of Index"+index+"Is not Found"));
        }
        employees.remove(index);
        return  ResponseEntity.status(200).body(new ApiResponse("The Employee of Index"+index+"Is Found"));
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<?> searchEmployeeByPosition(@PathVariable String position){
        ArrayList<Employee> pos=new ArrayList<>();
        if(!position.equalsIgnoreCase("supervisor")&&!position.equalsIgnoreCase("coordinator")){
            return  ResponseEntity.status(400).body(new ApiResponse("There is no Employee of position supervisor or coordinator"));
        }
        for(Employee e:employees){

            if(e.getPosition().equalsIgnoreCase(position)){
                pos.add(e);
            }
        }
        if(pos.isEmpty()){
            return  ResponseEntity.status(400).body(new ApiResponse("There is no Employee of position supervisor or coordinator"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The Employee of Position "+position+" are: "+pos));
    }

    @GetMapping("/get/{minAge}/{maxAge}")
    public ResponseEntity<?> getRangeOfAge(@PathVariable int minAge,@PathVariable int maxAge){
        ArrayList<Employee> range=new ArrayList<>();
        for(Employee e:employees){
            if(e.getAge()>=minAge && e.getAge()<=minAge){
                range.add(e);
            }
            if(range.isEmpty()){
                return ResponseEntity.status(400).body(new ApiResponse("There Is No Employee With That Range Of Age"));
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse("The Employee of Given Range are: "+range));
    }

    @PutMapping("/update/leave/{index}")
    public ResponseEntity<?> updateOnLeave(@PathVariable int index){
        if(index<0||index>=employees.size()){
            return ResponseEntity.status(400).body(new ApiResponse("The Index IS Out Of Range"));
        }
        if(employees.get(index).getAnnualLeave()>=1){
            int annual=employees.get(index).getAnnualLeave()-1;
            employees.get(index).setAnnualLeave(annual);
            employees.get(index).setOnLeave(true);
        }else if(employees.get(index).isOnLeave()){
            return ResponseEntity.status(400).body(new ApiResponse("The Employee Is Already On Leave"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The Employee got His Annual Leave"));
    }

    @GetMapping("/get/annualleave")
    public ResponseEntity<?> serachEmployeeByLeaves(Employee employee){
        ArrayList<Employee> zero=new ArrayList<>();
        for(Employee e:employees){
            if(e.getAnnualLeave()==0) {
                zero.add(e);
            }
        }
        if(zero.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There Is No Employee With That have zero Leaves "));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The Employee got zero Annual Leave : "+zero));
    }

    @PutMapping("/update/{name}/{id}")
    public ResponseEntity<?> PromptSupervor(@PathVariable String name, @PathVariable String id){
        for(Employee em:employees){
            if(em.getName().equalsIgnoreCase(name)&&!em.getPosition().equalsIgnoreCase("supervisor")){
                 return ResponseEntity.status(400).body(new ApiResponse("The Requster Is Not a Supervisor"));
            }
        }
        for(Employee e:employees){
            if(e.getId().equals(id)&&e.getAge()>=30&&!e.isOnLeave()){
                e.setPosition("supervisor");
                return ResponseEntity.status(200).body(new ApiResponse("The Employees Is Now A Supervior"));
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse(" "));
    }
}