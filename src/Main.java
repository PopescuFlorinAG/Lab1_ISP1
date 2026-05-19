import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Student {
    protected int numarMatricol;
    protected String prenume;
    protected String nume;
    protected String formatieDeStudiu;
    protected double nota;

    public Student(int numarMatricol, String prenume, String nume, String formatieDeStudiu, double nota) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
        this.nota = nota;
    }

    public int getNumarMatricol() { return numarMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formatieDeStudiu; }
    public double getNota() { return nota; }

    @Override
    public String toString() {
        return "Student " + numarMatricol + " | " + prenume + " " + nume + " | " + formatieDeStudiu + " | Nota: " + nota;
    }
}

interface IStudentiExport {
    void doExport(List<Student> studenti);
}

interface IStudentiImport {
    List<Student> doImport();
}

class Exporter {
    public void startExport(IStudentiExport strategy, List<Student> studenti) {
        strategy.doExport(studenti);
    }
}

class Importer {
    public List<Student> startImport(IStudentiImport strategy) {
        return strategy.doImport();
    }
}

// a)
class StudentiInConsola implements IStudentiExport {
    @Override
    public void doExport(List<Student> studenti) {
        System.out.println("\nconsola ");
        for (Student s : studenti) {
            System.out.println(s);
        }
    }
}

// b)
class StudentiInFisierText implements IStudentiExport {
    private String fileName;
    public StudentiInFisierText(String fileName) { this.fileName = fileName; }

    @Override
    public void doExport(List<Student> studenti) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Student s : studenti) {
                pw.println(s.getNumarMatricol() + ";" + s.getPrenume() + ";" + s.getNume() + ";" + s.getFormatieDeStudiu() + ";" + s.getNota());
            }
            System.out.println(fileName);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

// c)
class StudentiInFisierXlsx implements IStudentiExport {
    private String fileName;
    public StudentiInFisierXlsx(String fileName) { this.fileName = fileName; }

    @Override
    public void doExport(List<Student> studenti) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Studenti");
            int rand = 0;
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
            System.out.println("XLSX" + fileName);
        } catch (Exception e) { e.printStackTrace(); }
    }
}

// d)
class StudentiDinFisierText implements IStudentiImport {
    private String fileName;
    public StudentiDinFisierText(String fileName) { this.fileName = fileName; }

    @Override
    public List<Student> doImport() {
        List<Student> lista = new ArrayList<>();
        try {
            List<String> linii = Files.readAllLines(Paths.get(fileName));
            for (String linie : linii) {
                String[] p = linie.split(";");
                lista.add(new Student(Integer.parseInt(p[0]), p[1], p[2], p[3], Double.parseDouble(p[4])));
            }
            System.out.println("TXT");
        } catch (IOException e) { e.printStackTrace(); }
        return lista;
    }
}

// e)
class StudentiDinFisierXlsx implements IStudentiImport {
    private String fileName;
    public StudentiDinFisierXlsx(String fileName) { this.fileName = fileName; }

    @Override
    public List<Student> doImport() {
        List<Student> lista = new ArrayList<>();
        try (FileInputStream in = new FileInputStream(fileName);
             Workbook workbook = new XSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int id = (int) row.getCell(0).getNumericCellValue();
                String prenume = row.getCell(1).getStringCellValue();
                String nume = row.getCell(2).getStringCellValue();
                String formatie = row.getCell(3).getStringCellValue();
                double nota = row.getCell(4).getNumericCellValue();
                lista.add(new Student(id, prenume, nume, formatie, nota));
            }
            System.out.println("XLSX");
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}


class AplicatieCuStrategy {
     static void main(String[] args) {

        List<Student> studenti = Arrays.asList(
                new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10),
                new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                new Student(1029, "Bianca", "Popescu", "TI131/1", 10),
                new Student(1029, "Maria", "Pana", "TI131/2", 4.10),
                new Student(1029, "Gabriela", "Mohanu", "TI131/2", 7.33),
                new Student(1029, "Marius", "Nasta", "TI131/2", 3.20),
                new Student(1029, "Marius", "Nasta", "TI131/1", 5.12),
                new Student(1029, "Andrei", "Dobrescu", "TI131/2", 2.22)
        );

        Exporter exporter = new Exporter();
        Importer importer = new Importer();

        //Consola
        IStudentiExport strategyConsole = new StudentiInConsola();
        exporter.startExport(strategyConsole, studenti);

        //Txt
        System.out.println("\n--- Testare Export/Import fisiere ---");
        String textFile = "studentiStrategyText.txt";
        IStudentiExport strategyFisierText = new StudentiInFisierText(textFile);
        exporter.startExport(strategyFisierText, studenti);

        //Xlsx
        String excelFile = "studentiStrategyExcel.xlsx";
        IStudentiExport strategyFisierExcel = new StudentiInFisierXlsx(excelFile);
        exporter.startExport(strategyFisierExcel, studenti);

        //Txt
        IStudentiImport strategyImportText = new StudentiDinFisierText(textFile);
        List<Student> studentiDinText = importer.startImport(strategyImportText);

        //Xlsx
        IStudentiImport strategyImportExcel = new StudentiDinFisierXlsx(excelFile);
        List<Student> studentiDinExcel = importer.startImport(strategyImportExcel);

        System.out.println("\nVf" + studentiDinExcel.get(0));
    }
}
