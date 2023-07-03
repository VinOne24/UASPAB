package com.si61.listuniversitas.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelUniversitas> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelUniversitas> getData() {
        return data;
    }
}
