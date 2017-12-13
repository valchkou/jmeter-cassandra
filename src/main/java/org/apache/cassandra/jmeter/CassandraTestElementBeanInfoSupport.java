package org.apache.cassandra.jmeter;
/*
 * Copyright 2014 Steven Lowenthal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testbeans.gui.TypeEditor;

import java.beans.PropertyDescriptor;

public abstract class CassandraTestElementBeanInfoSupport extends BeanInfoSupport {

    /**
     *
     */
    public CassandraTestElementBeanInfoSupport(Class<? extends TestBean> beanClass) {
        super(beanClass);

        createPropertyGroup("varName", // $NON-NLS-1$
                new String[]{"sessionName" }); // $NON-NLS-1$

        createPropertyGroup("cql", // $NON-NLS-1$
                new String[] {
                "queryType", // $NON-NLS-1$
                "query", // $NON-NLS-1$
                "queryArguments", // $NON-NLS-1$
                "variableNames", // $NON-NLS-1$
                "resultVariable", // $NON-NLS-1$
                "consistencyLevel", // $NON-NLS-1$
                "batchSize" // $NON-NLS-1$
                });

        PropertyDescriptor p = property("sessionName"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

        p = property("queryArguments"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

        p = property("variableNames"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

        p = property("resultVariable"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$

        p = property("batchSize"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "");

        p = property("queryType"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, AbstractCassandraTestElement.SIMPLE);
        p.setValue(NOT_OTHER,Boolean.TRUE);
        p.setValue(TAGS,new String[]{
                AbstractCassandraTestElement.SIMPLE,
                AbstractCassandraTestElement.PREPARED,
                AbstractCassandraTestElement.DYNAMIC_BATCH
                });

        p = property("consistencyLevel"); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, "ONE");
        p.setValue(NOT_OTHER,Boolean.TRUE);
        p.setValue(TAGS,new String[]{
                AbstractCassandraTestElement.ANY,
                AbstractCassandraTestElement.ONE,
                AbstractCassandraTestElement.QUORUM,
                AbstractCassandraTestElement.TWO,
                AbstractCassandraTestElement.THREE,
                AbstractCassandraTestElement.ALL,
                AbstractCassandraTestElement.LOCAL_ONE,
                AbstractCassandraTestElement.LOCAL_QUORUM,
                AbstractCassandraTestElement.EACH_QUORUM
        });

        p = property("query", TypeEditor.TextAreaEditor); // $NON-NLS-1$
        p.setValue(NOT_UNDEFINED, Boolean.TRUE);
        p.setValue(DEFAULT, ""); // $NON-NLS-1$
        p.setValue(TEXT_LANGUAGE, "sql");  // $NON-NLS-1$

    }
}
