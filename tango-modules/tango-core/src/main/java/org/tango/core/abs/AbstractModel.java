package org.tango.core.abs;

import java.io.Serializable;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午2:16
 */
public abstract class AbstractModel {

    private Integer id;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
