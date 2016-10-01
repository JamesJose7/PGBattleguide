package com.pgbattleguide.jose.pgbattleguide.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose on 29/09/2016.
 */
public class CSVReader {

    InputStream inputStream;

    public CSVReader(InputStream is) {
        inputStream = is;
    }

    public List<String[]> read() {
        List<String[]> resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                resultList.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                inputStream.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }
}
