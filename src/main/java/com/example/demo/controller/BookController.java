package com.example.demo.controller;

import com.example.demo.domain.P;
import com.example.demo.domain.R;
import com.example.demo.domain.entity.Book;
import com.example.demo.util.PageUtil;
import com.google.common.collect.Lists;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author honghui 2021/07/07
 */
@RequestMapping("/book")
@RestController
public class BookController {

  //===========
  // find lists
  //===========
  @GetMapping
  public R<P<Book>> findBooks(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size,
      @RequestParam(required = false) String sortField,
      @RequestParam(required = false) Direction sortDirection) {
    return R.ok(
        new PageImpl<>(
            Lists.newArrayList(
                Book.builder().id(1L).name("水浒传").price(10).build(),
                Book.builder().id(1L).name("西游记").price(12).build()
            ),
            PageUtil.of(page, size, sortField, sortDirection),
            20
        )
    );
  }

  // or an easier way
//  @GetMapping
//  public RP<Book> findBooks2(
//      @RequestParam(required = false, defaultValue = "0") Integer page,
//      @RequestParam(required = false, defaultValue = "10") Integer size,
//      @RequestParam(required = false) String sortField,
//      @RequestParam(required = false) Direction sortDirection) {
//    return RP.ok(
//        new PageImpl<>(
//            Lists.newArrayList(
//                Book.builder().id(1L).name("水浒传").price(10).build(),
//                Book.builder().id(1L).name("西游记").price(12).build()
//            ),
//            PageUtil.of(page, size, sortField, sortDirection),
//            20
//        )
//    );
//  }

  //===========
  // find by id
  //===========
  @GetMapping("/{id}")
  public R<Book> findBook(@PathVariable Long id) {
    return R.ok(
        Book.builder().id(id).name("水浒传").price(10).build()
    );
  }

  //===========
  // find props
  //===========
  @GetMapping("/{id}/name")
  public R<String> findBookName(@PathVariable Long id) {
    return R.ok("水浒传");
  }

  //==============
  // find by props
  //==============
  @GetMapping("/_search")
  public R<Book> search(@RequestParam String name) {
    return R.ok(
        Book.builder().id(1L).name(name).price(10).build()
    );
  }

  //====================
  // batch find by props
  //====================
  @PostMapping("/_search/id")
  public R<List<Book>> search2(@RequestBody List<Long> idList) {
    return R.ok(
        idList.stream().map(id ->
            Book.builder().id(id).name("水浒传").price(10).build())
            .collect(Collectors.toList())
    );
  }

  // or a common way
  @PostMapping("/_search")
  public R<List<Book>> search3(@RequestBody Map<Object, List<Long>> query) {
    List<Long> idList = query.getOrDefault("id", Collections.emptyList());
    return search2(idList);
  }

  //==============================
  // create(always create new one)
  //==============================
  @PostMapping
  public R<Book> create(@RequestBody Book book) {
    return R.ok(book);
  }

//  @PostMapping
//  public R<Book> create2(@RequestParam String name) {
//    return R.ok(Book.builder().id(1L).name(name).build());
//  }

  //===================
  // create / overwrite
  //===================
  @PutMapping("/{id}")
  public R<Book> save(@RequestBody Book book) {
    return R.ok(book);
  }

//  @PutMapping("/{id}")
//  public R<Book> save2(@PathVariable Long id, @RequestParam String name) {
//    return R.ok(Book.builder().id(id).name(name).build());
//  }

  //=============
  // delete by id
  //=============
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {

  }

  //==============
  // custom action
  //==============
  @PostMapping("/cache/_clean")
  public void clean() {

  }

  //============
  // change prop
  //============
  @PatchMapping("/{id}")
  public R<Book> patch(@PathVariable Long id, @RequestParam String name) {
    return R.ok(Book.builder().id(id).name(name).build());
  }

//  @PatchMapping("/{id}")
//  public R<Book> patch(@PathVariable Long id, @RequestParam Book book) {
//    return R.ok(book);
//  }

}
