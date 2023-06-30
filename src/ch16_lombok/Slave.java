package ch16_lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
//@Setter
//@EqualsAndHashCode
//@ToString
@Data
public class Slave {
	private String name;
//	@Getter
	private int age;
}
