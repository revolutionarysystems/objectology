package uk.co.revsys.objectology.mapping.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uk.co.revsys.objectology.mapping.json.serialise.NullSerialiser;

@JsonSerialize(using = NullSerialiser.class)
public class GeneratedAttributeMixin {

}
