package io.pivotal.scdf.demo.task.common.key;

import com.gemstone.gemfire.DataSerializable;
import com.gemstone.gemfire.DataSerializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by zhansen on 10/7/16.
 */
public class RoyaltyScheduleKey implements DataSerializable {
    @Override
    public String toString() {
        return "RoyaltyScheduleKey{" +
                "titleId='" + titleId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoyaltyScheduleKey that = (RoyaltyScheduleKey) o;

        return titleId != null ? titleId.equals(that.titleId) : that.titleId == null;

    }

    @Override
    public int hashCode() {
        return titleId != null ? titleId.hashCode() : 0;
    }

    public String getTitleId() {

        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    private String titleId;

    @Override
    public void toData(DataOutput dataOutput) throws IOException {
        DataSerializer.writeObject(titleId, dataOutput);
    }

    @Override
    public void fromData(DataInput dataInput) throws IOException, ClassNotFoundException {
        titleId = DataSerializer.readObject(dataInput);
    }

}
