package com.backend.timkiemdong.controller;

import com.backend.timkiemdong.dto.ParaCurrencyRateDto;
import com.backend.timkiemdong.dto.SearchInput;
import com.backend.timkiemdong.dto.SearchResult;
import com.backend.timkiemdong.entity.ParaCurrencyRate;
import com.backend.timkiemdong.service.ParaCurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Currency")
@CrossOrigin(origins = "http://localhost:4200")
public class ParaCurrencyRateController {
    @Autowired
    ParaCurrencyRateService paraCurrencyRateService;


    @PostMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestBody ParaCurrencyRateDto paraCurrencyRateDto) {
        List result = paraCurrencyRateService.getAll(paraCurrencyRateDto);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có dữ liệu tương ứng");
        }
        return ResponseEntity.ok(result);
    }

    // hiển thị
    @PostMapping("/list")
    public ResponseEntity<?> list(@RequestBody SearchInput searchInput) {
        SearchResult result = paraCurrencyRateService.findList(searchInput);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có dữ liệu tương ứng");
        }
        return ResponseEntity.ok(result);
    }



    //tao moi
    @PostMapping("/Create")
    public ResponseEntity<ParaCurrencyRateDto> create(@RequestBody ParaCurrencyRateDto paraCurrencyRateDto) {
        return ResponseEntity.ok(paraCurrencyRateService.create(paraCurrencyRateDto));
    }

    // sửa
    @PostMapping("/Update")
    public ResponseEntity<ParaCurrencyRateDto> update( @RequestBody ParaCurrencyRateDto paraCurrencyRateDto) {
        return ResponseEntity.ok(paraCurrencyRateService.update( paraCurrencyRateDto));
    }

//    @PostMapping("/Update")
//    public ResponseEntity<ParaCurrencyRateDto> update(@RequestParam Long id, @RequestBody ParaCurrencyRateDto paraCurrencyRateDto) {
//        return ResponseEntity.ok(paraCurrencyRateService.update( id, paraCurrencyRateDto));
//    }


    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ParaCurrencyRate paraCurrencyRate) {
        paraCurrencyRateService.delete(paraCurrencyRate);
        return ResponseEntity.ok("Đã xoá thành công");
    }

//    @PostMapping("/delete")
//    public ResponseEntity<String> delete(@RequestParam Long id) {
//        paraCurrencyRateService.delete(id);
//        return ResponseEntity.ok("Đã xoá thành công");
//    }


    @PostMapping("/findBySpec")
    public ResponseEntity<?> searchSpec(@RequestBody SearchInput searchInput) {
        SearchResult result = paraCurrencyRateService.findBySpec(searchInput.getSearch(), searchInput);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có dữ liệu tương ứng");
        }
        return ResponseEntity.ok(result);
    }


    @PostMapping("/findByNav")
    public ResponseEntity<?> searchNav(@RequestBody SearchInput searchInput) {
        Map result = paraCurrencyRateService.findByNat(searchInput.getSearch(), searchInput);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có dữ liệu tương ứng");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/findByPro")
    public ResponseEntity<?> searchPro(@RequestBody SearchInput searchInput) {

        Map result = paraCurrencyRateService.finByPro(searchInput.getSearch(), searchInput);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có dữ liệu tương ứng");
        }
        return ResponseEntity.ok(result);
    }
}
