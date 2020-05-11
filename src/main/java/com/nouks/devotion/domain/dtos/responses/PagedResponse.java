package com.nouks.devotion.domain.dtos.responses;

import com.nouks.devotion.utils.PageLink;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResponse {
  private Object data;
  private List<PageLink> pageLinks;
}
