package com.vetrix.gest_API.projet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetsCount {
    private Integer new_project;
    private Integer assign_project;
    private Integer programme_project;
    private Integer work_in_progress;
    private Integer work_end;
    private Integer end_project;
}
