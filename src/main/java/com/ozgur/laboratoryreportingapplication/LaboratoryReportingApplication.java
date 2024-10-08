package com.ozgur.laboratoryreportingapplication;

import com.ozgur.laboratoryreportingapplication.dev_temp.DummyReportsDatabase;
import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import com.ozgur.laboratoryreportingapplication.service.ReportService;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.ReportSaveRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ozgur.laboratoryreportingapplication.entity.enums.RoleType;
import com.ozgur.laboratoryreportingapplication.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class LaboratoryReportingApplication implements CommandLineRunner/*, AsyncConfigurer*/ {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ReportService reportService;

    public static void main(String[] args) {
        SpringApplication.run(LaboratoryReportingApplication.class, args);
    }


//  CommandLineRunner is implemented and the run method is overridden.
//	In this way, when the application starts working, the following methods will be run first.
    @Override
    public void run(String... args) {

        // Fill the RoleType table
        if (userRoleService.getAllUserRole().isEmpty()) {
            userRoleService.save(RoleType.ROLE_ADMIN);
            userRoleService.save(RoleType.ROLE_LABORANT);
        }

        // Create ADMIN (built_in)
        if (!userService.isExistsAdmin()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("12345678");
            admin.setName("Halil");
            admin.setSurname("Akgun");
            admin.setHospitalIdNumber("0000000");
            admin.setFullName(admin.getName() + " " + admin.getSurname());
            userService.saveAdmin(admin);
        }
    }

    @Bean
    @Profile("dev")
    CommandLineRunner createDummyUsers() {
        return (args) -> {
            if (userRepository.count() < 2) {

                ReportSaveRequest report = new ReportSaveRequest();
                RegisterRequest user = new RegisterRequest();
                DummyReportsDatabase db = new DummyReportsDatabase();
                Random random = new Random();

                for (int i = 1; i <= 33; i++) {
                    user.setPassword("12345678");
                    int indexForName = random.nextInt(2);
                    user.setName(db.getPatientName(indexForName, random.nextInt(8)));
                    user.setSurname(db.getPatientSurname(indexForName, random.nextInt(8)));
                    user.setUsername(user.getName() + i);
                    if (i < 10) {
                        user.setHospitalIdNumber("000000" + i);
                    } else {
                        user.setHospitalIdNumber("00000" + i);
                    }
                    User savedUser = userService.saveUser(user);

                    for (int j = 1; j <= 3; j++) {
                        report.setDateOfReport(LocalDate.of(2022, random.nextInt(12) + 1, random.nextInt(28) + 1));
                        String monthValue = String.format("%02d", report.getDateOfReport().getMonthValue());
                        int indexForName2 = random.nextInt(2);
                        report.setPatientName(db.getPatientName(indexForName2, random.nextInt(8)));
                        report.setPatientSurname(db.getPatientSurname(indexForName2, random.nextInt(8)));
                        int indexForDiagnosis = random.nextInt(10);
                        report.setDiagnosisTitle(db.getDiagnosisTitle(indexForDiagnosis));
                        report.setDiagnosisDetails(db.getDiagnosisDetails(indexForDiagnosis));
                        report.setImageOfReport(j == 2 ? null : "sampleReport.png");
                        int min = 10000000;
                        int max = 99999999;
                        int randomIdNumber = random.nextInt(max - min + 1) + min;
                        if (i < 10) {
                            report.setFileNumber("2022" + monthValue + "0" + i + j);
                            report.setPatientIdNumber(randomIdNumber + "0" + i + j);
                        } else {
                            report.setFileNumber("2022" + monthValue + i + j);
                            report.setPatientIdNumber(randomIdNumber + "" + i + j);
                        }
                        reportService.saveDummyReports(report, savedUser);
                    }
                }
            }
        };
    }
}
