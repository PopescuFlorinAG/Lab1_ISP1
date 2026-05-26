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

class Exporter {
    public void startExport(IStudentiExport strategy, List<Student> studenti) {
        strategy.doExport(studenti);
    }
}

class StudentiInConsola implements IStudentiExport {
    @Override
    public void doExport(List<Student> studenti) {
        for (Student s : studenti) {
            System.out.println(s);
        }
    }
}

class StudentiInFisierText implements IStudentiExport {
    private String fileName;
    public StudentiInFisierText(String fileName) { this.fileName = fileName; }

    @Override
    public void doExport(List<Student> studenti) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Student s : studenti) {
                pw.println(s.getNumarMatricol() + ";" + s.getPrenume() + ";" + s.getNume() + ";" + s.getFormatieDeStudiu() + ";" + s.getNota());
            }
            System.out.println("Export TXT finalizat: " + fileName);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

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
            System.out.println("Export XLSX finalizat: " + fileName);
        } catch (Exception e) { e.printStackTrace(); }
    }
}


abstract class StudentiExportDecorator implements IStudentiExport {
    protected IStudentiExport decoratedExport;

    public StudentiExportDecorator(IStudentiExport decoratedExport) {
        this.decoratedExport = decoratedExport;
    }

    @Override
    public void doExport(List<Student> studenti) {
        decoratedExport.doExport(studenti);
    }
}

class TimeMeasuringExportDecorator extends StudentiExportDecorator {
    public TimeMeasuringExportDecorator(IStudentiExport decoratedExport) {
        super(decoratedExport);
    }

    @Override
    public void doExport(List<Student> studenti) {

        long startTime = System.currentTimeMillis();
        super.doExport(studenti);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Timpul de executie: " + duration + " ms.\n");
    }
}

public class Main {
    public static void main(String[] args) {

        List<Student> studenti = Arrays.asList(
                new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10),
                new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                new Student(1029, "Bianca", "Popescu", "TI131/1", 10),
                new Student(1029, "Maria", "Pana", "TI131/2", 4.10)
        );

        Exporter exporter = new Exporter();


        IStudentiExport strategyConsole = new StudentiInConsola();
        IStudentiExport decoratedConsole = new TimeMeasuringExportDecorator(strategyConsole);
        exporter.startExport(decoratedConsole, studenti);

        String textFile = "studentiStrategyText.txt";
        IStudentiExport strategyFisierText = new StudentiInFisierText(textFile);
        IStudentiExport decoratedFisierText = new TimeMeasuringExportDecorator(strategyFisierText);
        exporter.startExport(decoratedFisierText, studenti);

        String excelFile = "studentiStrategyExcel.xlsx";
        IStudentiExport strategyFisierExcel = new StudentiInFisierXlsx(excelFile);
        IStudentiExport decoratedFisierExcel = new TimeMeasuringExportDecorator(strategyFisierExcel);
        exporter.startExport(decoratedFisierExcel, studenti);
    }
}
