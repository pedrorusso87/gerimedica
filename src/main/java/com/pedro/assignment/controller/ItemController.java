package com.pedro.assignment.controller;

import com.pedro.assignment.controller.exception.ItemNotFoundException;
import com.pedro.assignment.model.Item;
import com.pedro.assignment.respository.ItemRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController()
public class ItemController {

  @Autowired
  ItemRepository itemService;

  // Read CSV File and save data into h2 db
  @PostMapping("/upload")
  public String uploadCSVFile(@RequestParam("file") MultipartFile file) throws Exception{
    List<Item> itemsList = new ArrayList<>();
    InputStream inputStream = file.getInputStream();

    CsvParserSettings csvParserSettings = new CsvParserSettings();
    csvParserSettings.setHeaderExtractionEnabled(true);

    CsvParser csvParser = new CsvParser(csvParserSettings);
    List<Record> recordList = csvParser.parseAllRecords(inputStream);

    itemService.saveAll(buildItemsList(recordList));
    return "Upload finished successfully";
  }

  @GetMapping("items")
  public ResponseEntity<List<Item>> getItems() {
    List<Item> items = itemService.findAll();
    return new ResponseEntity<>(items, HttpStatus.OK);
  }

  @GetMapping("items/{code}")
  public ResponseEntity<Item> getItemsById(@PathVariable @RequestBody String code) {

    return new ResponseEntity<>(findItemByCode(code), HttpStatus.OK);
  }

  @DeleteMapping("items/{code}")
  public ResponseEntity<String> deleteItem(@PathVariable @RequestBody String code) {
    itemService.delete(findItemByCode(code));
    return new ResponseEntity<String>("Item deleted", HttpStatus.OK);
  }

  private Item findItemByCode(String code) {
    return itemService.findById(code).orElseThrow(() -> new ItemNotFoundException("Item not found with that code"));
  }

  private List<Item> buildItemsList(List<Record> recordList) {
    List<Item> itemList = new ArrayList<>();
    recordList.forEach(record -> {
      Item item = new Item();
      item.setCode(record.getString("code"));
      item.setSource(record.getString("source"));
      item.setCodeListCode(record.getString("codeListCode"));
      item.setDisplayValue(record.getString("displayValue"));
      item.setFromDate(record.getString("fromDate"));
      item.setToDate(record.getString("toDate"));
      item.setLongDescription(record.getString("longDescription"));
      try {

        item.setSortingPriority(Integer.parseInt(record.getString("sortingPriority")));
      } catch (NumberFormatException e) {
        System.out.println("No Sorting priority found for record " + record.getString("code"));
      }
      itemList.add(item);
    });
    return itemList;
  }
}
