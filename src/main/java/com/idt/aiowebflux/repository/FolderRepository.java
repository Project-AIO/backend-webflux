package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.dto.ParentNodeDto;
import com.idt.aiowebflux.entity.Folder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {


    @Query(value = """
            WITH RECURSIVE path_cte AS (
              SELECT folder_id, parent_id, name, 0 AS lvl
              FROM   tb_folder
              WHERE  folder_id = :folderId      -- start node
              UNION ALL
              SELECT f.folder_id, f.parent_id, f.name, lvl + 1
              FROM   tb_folder f
                     JOIN path_cte p ON p.parent_id = f.folder_id
            )
            SELECT GROUP_CONCAT(name ORDER BY lvl DESC SEPARATOR '/') AS full_path
            FROM   path_cte;
            """, nativeQuery = true)
    String findFullPath(@Param("folderId") final Long folderId);


    @Query(value = """
            WITH RECURSIVE ancestors AS (
               -- 시작 노드(depth = 0)
               SELECT folder_id,
                      name,
                      parent_id,
                      0 AS depth
                 FROM tb_folder
                WHERE folder_id = :id
                        
              UNION ALL
                        
               -- 부모 쪽으로 한 단계씩 올라가며 depth + 1
               SELECT f.folder_id,
                      f.name,
                      f.parent_id,
                      a.depth + 1
                 FROM tb_folder f
                 JOIN ancestors a
                   ON f.folder_id = a.parent_id
            )
            SELECT folder_id,
                   name
              FROM ancestors
             WHERE folder_id <> :id    -- 현재 노드는 제외
             ORDER BY depth DESC        -- depth 1, 2, 3... 순
            """,
            nativeQuery = true)
    List<ParentNodeDto> findAncestorsById(@Param("id") Long id);

}
