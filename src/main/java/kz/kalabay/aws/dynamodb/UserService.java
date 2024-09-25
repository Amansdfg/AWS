package kz.kalabay.aws.dynamodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;


@Service
public class UserService {
    private final DynamoDbTable<User> userTable;
    @Autowired
    public UserService(DynamoDbTable<User> userTable) {
        this.userTable = userTable;
    }

    public void saveUser(User user) {
        userTable.putItem(user);
    }

    public User getUserById(String id) {
        Key key = Key.builder()
            .partitionValue(id)
            .build();
        return userTable.getItem(r -> r.key(key));
    }

    public void deleteUserById(String id) {
        Key key = Key.builder()
            .partitionValue(id)
            .build();
        userTable.deleteItem(r -> r.key(key));
    }
}
