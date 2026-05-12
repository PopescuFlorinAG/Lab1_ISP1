import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Laborator8Studenti {

    public static void writeToXls(Set<Student> studenti, String fileName) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Lista Studenti");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nr Matricol");
            headerRow.createCell(1).setCellValue("Prenume");
            headerRow.createCell(2).setCellValue("Nume");
            headerRow.createCell(3).setCellValue("Formatie Studiu");
            headerRow.createCell(4).setCellValue("Nota");

            int rand = 1;
            for (Student st : studenti) {
                Row row = sheet.createRow(rand++);
                row.createCell(0).setCellValue(st.getNumarMatricol());
                row.createCell(1).setCellValue(st.getPrenume());
                row.createCell(2).setCellValue(st.getNume());
                row.createCell(3).setCellValue(st.getFormatieDeStudiu());
                row.createCell(4).setCellValue(st.getNota());
            }

            try (FileOutputStream out = new FileOutputStream(fileName)) {
                workbook.write(out);
            }
            System.out.println("Date transferate:" + fileName);

        } catch (Exception e) {
            System.err.println("Eroare " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static List<Student> readFromXls(String fileName) {
        List<Student> listaCitita = new ArrayList<>();

        try (FileInputStream in = new FileInputStream(fileName);
             Workbook workbook = new XSSFWorkbook(in)) {

            Sheet sheet = workbook.getSheetAt(0);


            for (Row row : sheet) {

                if (row.getRowNum() == 0) {
                    continue;
                }

                int nrMatricol = (int) row.getCell(0).getNumericCellValue();
                String prenume = row.getCell(1).getStringCellValue();
                String nume = row.getCell(2).getStringCellValue();
                String formatie = row.getCell(3).getStringCellValue();
                double nota = row.getCell(4).getNumericCellValue();

                Student st = new Student(nrMatricol, prenume, nume, formatie, nota);
                listaCitita.add(st);
            }

            System.out.println("Datele importate" + fileName);

        } catch (Exception e) {
            System.err.println("Eroare " + e.getMessage());
            e.printStackTrace();
        }

        return listaCitita;
    }

    public static void main(String[] args) {
        String xlsFileName = "laborator8_students.xls";

        Set<Student> studenti = new HashSet<>();
        studenti.add(new Student(1024, "Ioan", "Mihalcea", "ISM141", 9.80));
        studenti.add(new Student(1025, "Andrei", "Popa", "ISM141", 8.70));
        studenti.add(new Student(1026, "Anamaria", "Prodan", "TI131", 8.90));

        writeToXls(studenti, xlsFileName);

        List<Student> studentsFromXls = readFromXls(xlsFileName);

        System.out.println("\nStudenti:");
        for (Student st : studentsFromXls) {
            System.out.println(st);
        }
    }
}
