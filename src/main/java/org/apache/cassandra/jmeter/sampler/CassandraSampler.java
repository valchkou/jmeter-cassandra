package org.apache.cassandra.jmeter.sampler;
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

import com.datastax.driver.core.Session;
import org.apache.cassandra.jmeter.AbstractCassandraTestElement;
import org.apache.cassandra.jmeter.config.CassandraConnection;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.engine.util.ConfigMergabilityIndicator;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A org.apache.cassandra.jmeter.sampler which understands Cassandra database requests.
 *
 */
public class CassandraSampler extends AbstractCassandraTestElement implements Sampler, TestBean, ConfigMergabilityIndicator {
    private static final Set<String> APPLIABLE_CONFIG_CLASSES = new HashSet<String>(
            Arrays.asList(new String[]{
                    "org.apache.jmeter.org.apache.cassandra.jmeter.config.gui.SimpleConfigGui"}));
    
    private static final long serialVersionUID = 234L;
    
    private static final Logger log = LoggingManager.getLoggerForClass();

    /**
     * Creates a CassandraSampler.
     */
    public CassandraSampler() {
    }

    public SampleResult sample(Entry e) {
        log.debug("sampling CQL");

        SampleResult res = new SampleResult();
        res.setSampleLabel(getName());
        res.setSamplerData(toString());
        res.setDataType(SampleResult.TEXT);
        res.setContentType("text/plain"); // $NON-NLS-1$
        res.setDataEncoding(ENCODING);

        // Assume we will be successful
        res.setSuccessful(true);
        res.setResponseMessageOK();
        res.setResponseCodeOK();


        res.sampleStart();
        Session conn = null;

        try {
            if(JOrphanUtils.isBlank(getSessionName())) {
                throw new IllegalArgumentException("Variable Name must not be null in "+getName());
            }

            try {
                conn = CassandraConnection.getSession(getSessionName());
            } finally {
                res.latencyEnd(); // use latency to measure connection time
            }
            res.setResponseHeaders(conn.toString());
            res.setResponseData(execute(conn));
        }  catch (Exception ex) {
            res.setResponseMessage(ex.toString());
            res.setResponseCode("000");
            res.setResponseData(ex.getMessage().getBytes());
            res.setSuccessful(false);
        }
// Doesn't apply
//        finally {
//            close(conn);
//        }

        // TODO: process warnings? Set Code and Message to success?
        res.sampleEnd();
        return res;
    }

    /**
     * @see org.apache.jmeter.samplers.AbstractSampler#applies(org.apache.jmeter.config.ConfigTestElement)
     */
    
    public boolean applies(ConfigTestElement configElement) {
        String guiClass = configElement.getProperty(TestElement.GUI_CLASS).getStringValue();
        return APPLIABLE_CONFIG_CLASSES.contains(guiClass);
    }
}
