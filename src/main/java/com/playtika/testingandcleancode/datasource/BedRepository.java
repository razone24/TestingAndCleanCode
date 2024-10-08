package com.playtika.testingandcleancode.datasource;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.playtika.testingandcleancode.domain.Bed;

public class BedRepository {
   private String staffFile = "/beds.csv";
   private Scanner scanner;

   public BedRepository() {
      try {
         URL fileUrl = getClass().getResource(staffFile);
         this.scanner = new Scanner(new File(fileUrl.getFile()));
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public List<Bed> getAllBeds() {
      ArrayList<Bed> bedList = new ArrayList<>();
      while (scanner.hasNextLine()) {
         String line = scanner.nextLine();
         String[] values = line.split(",");
         int bedId = Integer.parseInt(values[0]);
         boolean equippedCrit = Boolean.parseBoolean(values[1]);
         Bed bed = new Bed(bedId, equippedCrit);
         bedList.add(bed);
      }
      return bedList;
   }

}
