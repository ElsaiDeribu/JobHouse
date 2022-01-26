package com.jobhouse.finder.Helpers;

import java.io.Serializable;

import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Choose implements Serializable {
    private static final long serialVersionUID = 1L;
    public String choice;
}
