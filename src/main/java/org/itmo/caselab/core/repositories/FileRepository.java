package org.itmo.caselab.core.repositories;

import org.itmo.caselab.core.pojo.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
