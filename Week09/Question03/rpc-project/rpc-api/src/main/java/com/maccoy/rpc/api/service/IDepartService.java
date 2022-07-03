package com.maccoy.rpc.api.service;

import com.maccoy.rpc.api.domain.Depart;

public interface IDepartService {

    Depart selectDepartById(Integer departId);

}
