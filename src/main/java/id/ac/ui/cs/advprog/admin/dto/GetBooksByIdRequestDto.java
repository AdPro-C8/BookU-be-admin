package id.ac.ui.cs.advprog.admin.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class GetBooksByIdRequestDto {
    private List<UUID> bookIds;
}
