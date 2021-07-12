package de.jpdigital.hibernate.entites;


import org.hibernate.envers.Audited;

import java.sql.Blob;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Audited
public class BinaryAsset {
    
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long fileId;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "asset_data")
    private Blob data;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(final long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public Blob getData() {
        return data;
    }

    public void setData(final Blob data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (this.fileId ^ (this.fileId >>> 32));
        hash = 47 * hash + Objects.hashCode(this.fileName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BinaryAsset)) {
            return false;
        }
        final BinaryAsset other = (BinaryAsset) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.fileId != other.getFileId()) {
            return false;
        }
        return Objects.equals(this.fileName, other.getFileName());
    }
    
    public boolean canEqual(final Object obj) {
        return obj instanceof BinaryAsset;
    }
    
}
