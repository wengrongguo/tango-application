import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: tango
 * Date: 13-12-16
 * Time: 下午9:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-framework.xml")
public class TransformTest {

    @Autowired
    private DataSource dataSource;

    private Snapshot snapshot;

    @Before
    public void setup() throws SQLException {
        snapshot = new SqlQuerySnapshot(dataSource);
        snapshot.extract("GTP");
        snapshot.extract("NPC");
    }

    @Test
    public void testTransform() throws SQLException {
        transform("area", new String[]{"GTP", "NPC"});
    }

    private void transform(String h, String[] columns) throws SQLException {
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> dates = new ArrayList<String>();
        List<String> areas = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            List<Index> extract = snapshot.extract(column);
            //
            for (int j = 0; j < extract.size(); j++) {
                dates.add(extract.get(j).date);
                areas.add(extract.get(j).area);
                values.add(extract.get(j).value);
            }
        }
        print(dates, areas, values);
        System.out.println("--------------- soft Before --------------");
        //sort
        if (h.equals("date")) {
            soft(dates, dates, areas, values);
        } else if (h.equals("area")) {
            soft(areas, dates, areas, values);
        }
        print(dates, areas, values);
        result.add(dates);
        result.add(areas);
        result.add(values);

        System.out.println("result = " + result);
    }

    private void print(List<String> dates, List<String> areas, List<String> values) {
        System.out.println("时间");
        System.out.println(dates);
        System.out.println("区域");
        System.out.println(areas);
        System.out.println("指标");
        System.out.println(values);
    }

    private void soft(List<String> sorts, List<String> dates, List<String> areas, List<String> values) {
        int size = sorts.size();
        for (int i = size - 1; i > 0; i--) {
            for (int j = size - 1; j > 0; j--) {
                int index = j - 1;
                if (sorts.get(index).compareTo(sorts.get(j)) == 1) {
                    String sort = sorts.get(index);
                    sorts.set(index, sorts.get(j));
                    sorts.set(j, sort);
                    if (!sorts.equals(dates)) {
                        String date = sorts.get(index);
                        sorts.set(index, sorts.get(j));
                        sorts.set(j, date);
                    }
                    if (!sorts.equals(areas)) {
                        String area = areas.get(index);
                        areas.set(index, areas.get(j));
                        areas.set(j, area);
                    }
                    if (!sorts.equals(values)) {
                        String value = values.get(index);
                        values.set(index, values.get(j));
                        values.set(j, value);
                    }
                }
            }
        }
    }


    private static class Index {
        private String date;
        private String area;
        private String index;
        private String value;

        @Override
        public String toString() {
            return "Index{" +
                    "date='" + date + '\'' +
                    ", area='" + area + '\'' +
                    ", index='" + index + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public abstract static class Snapshot {

        private Map<String, List<Index>> storage = new HashMap<String, List<Index>>();


        public boolean contains(String index) {
            return storage.containsKey(index);
        }

        public List<Index> extract(String key) throws SQLException {
            if (storage.containsKey(key)) {
                return storage.get(key);
            } else {
                List<Index> indexes = push(key);
                storage.put(key, indexes);
                return indexes;
            }
        }

        protected abstract List<Index> push(String key) throws SQLException;
    }

    public static class SqlQuerySnapshot extends Snapshot {

        private DataSource dataSource;

        public SqlQuerySnapshot(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        protected List<Index> push(String key) throws SQLException {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from indexd where indexd = ?");
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Index> indexes = new ArrayList<Index>();
            while (resultSet.next()) {
                Index index = new Index();
                index.index = resultSet.getString("indexd");
                index.area = resultSet.getString("area");
                index.date = resultSet.getString("date");
                index.value = resultSet.getString("value");
                indexes.add(index);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return indexes;
        }
    }
}
