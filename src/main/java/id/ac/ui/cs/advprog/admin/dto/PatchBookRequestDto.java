package id.ac.ui.cs.advprog.admin.dto;

import java.sql.Date;

import lombok.Getter;

@Getter
public class PatchBookRequestDto {
    private String publisher;
    private int price;
    private Date tanggalTerbit;
    private String isbn;
    private int jumlahHalaman;
    private String fotoCover;
}