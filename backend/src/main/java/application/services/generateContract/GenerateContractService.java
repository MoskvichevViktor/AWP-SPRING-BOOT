package application.services.generateContract;

import application.dto.CreditResponseDto;
import application.services.calculate.Calculation;
import application.services.calculate.InputData;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Service
@Data
public class GenerateContractService {
    //private final String url;
    //private final String urlCreateFile;
    //private final String urlCreateTest;

    //@SneakyThrows
    public GenerateContractService() throws IOException {
        //Resource resource = new ClassPathResource("C:\\Users\\belde\\Documents\\GB\\Команда\\6\\backend\\src\\main\\resources\\blankContract\\template.docx");
        //Resource resource1 = new ClassPathResource("C:\\Users\\belde\\Documents\\GB\\Команда\\6\\backend\\src\\main\\resources\\blankContract\\createFileFromTemplate.docx");
       // Resource resource2 = new ClassPathResource("C:\\Users\\belde\\Documents\\GB\\Команда\\6\\backend\\src\\main\\resources\\blankContract\\templateTest.docx");
        //url = resource.getFile().getAbsolutePath();
        //urlCreateFile = resource1.getFile().getAbsolutePath();
        //urlCreateTest = resource2.getFile().getAbsolutePath();
    }

    public void generate(CreditResponseDto creditResponseDto) throws IOException, InvalidFormatException {
        createDocFromTemplate(creditResponseDto);
        replaceTextFromTemplate(creditResponseDto);
        backTemplate();
        //File file = new File("./backend/src/main/resources/blankContract/createFileFromTemplate.docx");
        //Desktop desktop = Desktop.getDesktop();
        //desktop.open(file);
        Process builder = Runtime.getRuntime().exec("cmd /c start C:\\Users\\belde\\Documents\\GB\\Команда\\6\\backend\\src\\main\\resources\\blankContract\\createFileFromTemplate.docx");

    }


    private  void backTemplate() throws IOException, InvalidFormatException {
        XWPFDocument doc2 = new XWPFDocument(OPCPackage.open("./backend/src/main/resources/blankContract/templateTest.docx"));
        doc2.write(new FileOutputStream("./backend/src/main/resources/blankContract/blankContract\\template.docx"));
        doc2.close();
    }


    private  void createDocFromTemplate(CreditResponseDto dto) throws InvalidFormatException, IOException{

        XWPFDocument doc = new XWPFDocument(OPCPackage.open("./backend/src/main/resources/blankContract/template.docx"));
        //добавление графика платежей
        BigDecimal sum = dto.getSum();
        float percent = dto.getPercent();
        int period = dto.getPeriod();
       InputData inputData =new InputData(dto.getSum(), dto.getPercent(), dto.getPeriod());


        Calculation calculation = new Calculation();
        calculation.getOutputDataArrays(inputData);

        XWPFTable table = doc.getTables().get(0);

        for(int i = 0; i < period; i++){
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(Objects.toString(calculation.getOutputDataArrays(new InputData(sum, percent, period)).get(i).getMonth()));
            tableRow.getCell(1).setText(Objects.toString(calculation.getOutputDataArrays(new InputData(sum, percent, period)).get(i).getPayment()));
            tableRow.getCell(2).setText(Objects.toString(calculation.getOutputDataArrays(new InputData(sum, percent, period)).get(i).getPartPayment()));
            tableRow.getCell(3).setText(Objects.toString(calculation.getOutputDataArrays(new InputData(sum, percent, period)).get(i).getPartPercent()));
            tableRow.getCell(4).setText(Objects.toString(calculation.getOutputDataArrays(new InputData(sum, percent, period)).get(i).getPartSum()));
        }
        doc.write(new FileOutputStream("./backend/src/main/resources/blankContract/createFileFromTemplate.docx"));
        doc.close();
    }

    private  void replaceTextFromTemplate(CreditResponseDto dto) throws InvalidFormatException, IOException{
        //Open doc templatet file
        XWPFDocument doc = new XWPFDocument(OPCPackage.open("./backend/src/main/resources/blankContract/template.docx"));
        //замена параметров
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("IDContract")) {
                        text = text.replace("IDContract", String.valueOf(dto.getContractId()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Date")) {
                        text = text.replace("Date", "2022 г.");
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Sum")) {
                        text = text.replace("Sum", String.valueOf(dto.getSum()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Period")) {
                        text = text.replace("Period", String.valueOf(dto.getPeriod()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Percent")) {
                        text = text.replace("Percent", String.valueOf(dto.getPercent()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Name")) {
                        text = text.replace("Name", String.valueOf(dto.getClientName()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Pasport")) {
                        text = text.replace("Pasport", String.valueOf(dto.getPasport()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Address")) {
                        text = text.replace("Address", String.valueOf(dto.getAddress()));
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("Phone")) {
                        text = text.replace("Phone", String.valueOf(dto.getPhone()));
                        r.setText(text, 0);
                    }
                }
            }
        }

        doc.write(new FileOutputStream("./backend/src/main/resources/blankContract/createFileFromTemplate.docx"));
        doc.close();
    }
}
