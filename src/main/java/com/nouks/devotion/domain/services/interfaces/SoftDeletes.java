package com.nouks.devotion.domain.services.interfaces;

import java.util.List;

public interface SoftDeletes<T, ID> {
  void trash(Long id);
  List<T> getTrashed();
  List<T> restoreTrashed(List<ID> Ids);
  List<T> restoreAllTrashed();
}
