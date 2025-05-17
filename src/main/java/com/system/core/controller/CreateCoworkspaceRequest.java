package com.system.core.controller;

import com.system.core.entity.CoWorkspace;
import com.system.core.entity.Equipment;

import java.util.List;
public class CreateCoworkspaceRequest {
    private CoWorkspace coWorkspace;
    private List<Equipment> equipmentNames;

    public CoWorkspace getCoWorkspace() {
        return coWorkspace;
    }

    public void setCoWorkspace(CoWorkspace coWorkspace) {
        this.coWorkspace = coWorkspace;
    }

    public List<Equipment> getEquipmentNames() {
        return equipmentNames;
    }

    public void setEquipmentNames(List<Equipment> equipmentNames) {
        this.equipmentNames = equipmentNames;
    }
}