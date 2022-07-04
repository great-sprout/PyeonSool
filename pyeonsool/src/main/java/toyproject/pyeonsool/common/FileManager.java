package toyproject.pyeonsool.common;

import org.springframework.stereotype.Component;
import toyproject.pyeonsool.domain.AlcoholType;

@Component
public class FileManager {

    private final String ALCOHOL_IMAGE_PATH = "/image/alcohol";


    public String getAlcoholImagePath(AlcoholType type, String fileName) {
        return ALCOHOL_IMAGE_PATH + "/" + type.toString().toLowerCase() + "/" + fileName;
    }
}
