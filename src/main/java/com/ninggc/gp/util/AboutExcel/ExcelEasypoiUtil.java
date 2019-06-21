package com.ninggc.gp.util.AboutExcel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.ninggc.gp.data.User;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelEasypoiUtil {
    public List<ExcelUser> importFromExcel(String path, String filename) throws FileNotFoundException {
        File file = new File(path);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        ImportParams params = new ImportParams();
//        params.setHeadRows(1);
        params.setTitleRows(1);

        List<ExcelUser> list = ExcelImportUtil.importExcel(file, ExcelUser.class, params);

        return list;
    }

    public void export(String path, String filename, List<ExcelUser> list) {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有学生", "学生"), ExcelUser.class, list);

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File exportFile(List<User> users) {

        try {
            ArrayList<ExcelUser> excelUsers = new ArrayList<>();
            for (User user : users) {
                ExcelUser excelUser = new ExcelUser();
                excelUser.setAccount(user.getAccount());
                excelUser.setPass_word(user.getPass_word());
                excelUser.setName(user.getName());
                excelUser.setAddition(user.getAddition());

                excelUsers.add(excelUser);
            }
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有学生", "学生"), ExcelUser.class, excelUsers);

            File file = new File("export.xls");
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
