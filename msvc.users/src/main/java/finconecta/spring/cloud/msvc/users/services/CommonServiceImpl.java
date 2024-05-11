package finconecta.spring.cloud.msvc.users.services;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonServiceImpl implements CommonService {

	private static final String EMPTY = "";
	private static final String POM_XML = "pom.xml";

	@Override
	@Transactional(readOnly = true)
	public String getVersion() {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model;
		try {
			if ((new File(POM_XML)).exists())
				model = reader.read(new FileReader(POM_XML));
			else
				model = reader.read(new InputStreamReader(CommonServiceImpl.class.getResourceAsStream(
						"/META-INF/maven/finconecta.spring.cloud/msvc.users/pom.xml")));
			return model.getVersion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY;
	}
}
