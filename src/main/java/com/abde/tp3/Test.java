package com.abde.tp3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Test {
  // private static String HOME = System.getProperty("user.home");
  // private static String fileuploads = "\\fileuploads";

  // public static void main(String[] args) throws IOException {
  // System.out.println(HOME);
  // Path p = Paths.get(HOME);
  // Path uploads = Paths.get(HOME + fileuploads);
  // if (Files.exists(p)) {
  // System.out.println(HOME + " exists");
  // if (Files.notExists(uploads)) {
  // Files.createDirectory(uploads);
  // }
  // Path file = Paths.get(HOME + fileuploads + "\\" +
  // generateFilePath("npm.txt"));
  // if (Files.notExists(file)) {
  // System.out.println(file.getFileName());
  // Files.createFile(file);
  // }
  // }
  // // Files.createDirectories(p, fileuploads);
  // System.out.println(Files.exists(p));
  // }

  // static private String generateFilePath(String filename) {
  // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
  // String timestamp = dateFormat.format(new Date());
  // String[] parts = filename.split("\\.(?=[^.]*$)", 2);
  // String name = "";
  // String extension = "";
  // if (parts.length == 2) {
  // name = parts[0];
  // extension = parts[1];
  // } else {
  // name = parts[0];
  // }
  // return name + timestamp + "." + extension;
  // }
  public static void main(String[] args) {
    // create a REGEX String
    String REGEX = "(.*)(for)(.*)?";

    // create the string in which you want
    // to search
    String actualString = "code of Machine";

    // create pattern
    Pattern pattern1 = Pattern.compile(REGEX);

    // find the regular expression of pattern
    String RegularExpression = pattern1.pattern();

    System.out.println("Pattern's RegularExpression = "
        + RegularExpression);
  }
}
