/**
 * Copyright DataStax, Inc.
 * <p>
 * Please see the included license file for details.
 */
package com.thinkaurelius.titan.diskstorage.hbase;

import java.io.Closeable;
import java.io.IOException;

import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

/**
 * This interface hides ABI/API breaking changes that HBase has made to its Admin/HBaseAdmin over the course
 * of development from 0.94 to 1.0 and beyond.
 */
public interface AdminMask extends Closeable
{

    void clearTable(String tableName, long timestamp) throws IOException;

    HTableDescriptor getTableDescriptor(String tableName) throws TableNotFoundException, IOException;

    boolean tableExists(String tableName) throws IOException;

    void createTable(HTableDescriptor desc) throws IOException;

    void createTable(HTableDescriptor desc, byte[] startKey, byte[] endKey, int numRegions) throws IOException;

    /**
     * Estimate the number of regionservers in the HBase cluster.
     *
     * This is usually implemented by calling
     * {@link HBaseAdmin#getClusterStatus()} and then
     * {@link ClusterStatus#getServers()} and finally {@code size()} on the
     * returned server list.
     *
     * @return the number of servers in the cluster or -1 if it could not be determined
     */
    int getEstimatedRegionServerCount();

    void disableTable(String tableName) throws IOException;

    void enableTable(String tableName) throws IOException;

    boolean isTableDisabled(String tableName) throws IOException;

    void addColumn(String tableName, HColumnDescriptor columnDescriptor) throws IOException;
}
