package cs590.awesomedelivery.searchsvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "restaurant")
public class Restaurant implements Serializable {

    @Id
    private String id;
    private String name;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Address address;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Menu> menu;
}