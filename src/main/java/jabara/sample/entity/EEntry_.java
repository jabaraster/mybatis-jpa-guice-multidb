package jabara.sample.entity;

import jabara.jpa.entity.EntityBase_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-01-09T10:46:23.388+0900")
@StaticMetamodel(EEntry.class)
public class EEntry_ extends EntityBase_ {
	public static volatile SingularAttribute<EEntry, String> title;
	public static volatile SingularAttribute<EEntry, String> text;
	public static volatile SetAttribute<EEntry, EKeyword> keywords;
	public static volatile SingularAttribute<EEntry, ESource> source;
}
