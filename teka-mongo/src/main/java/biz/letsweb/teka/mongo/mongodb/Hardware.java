package biz.letsweb.teka.mongo.mongodb;

import com.google.common.collect.Lists;
import biz.letsweb.teka.mongo.rest.validation.ValidHardware;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.data.annotation.Id;

@ValidHardware(message = "Manufacturer & Model fields must exist and have a value.")
public class Hardware {

    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    private String manufacturer;
    private String model;
    private final Set<String> firmware;

    public Hardware() {
        this.firmware = new TreeSet<>();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<String> getFirmware() {
        return Lists.newArrayList(firmware.toArray(new String[]{}));
    }

    public void setFirmware(String... firmware) {
        this.firmware.addAll(Arrays.asList(firmware));
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Hardware[id=%s, firstName='%s', lastName='%s', firmware='%s']", id,
                manufacturer, model, firmware);
    }

    private Hardware(Builder builder) {
        this.id = builder.id;
        this.manufacturer = builder.manufacturer;
        this.model = builder.model;
        this.firmware = builder.firmware;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        @Id
        @ApiModelProperty(hidden = true)
        private String id;
        private String manufacturer;
        private String model;
        private final Set<String> firmware;

        public Builder() {
            this.firmware = new TreeSet<>();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder withModel(String model) {
            this.model = model;
            return this;
        }

        public Builder withFirmware(String... firmware) {
            this.firmware.addAll(Arrays.asList(firmware));
            return this;
        }

        public Hardware build() {
            return new Hardware(this);
        }
    }

}
