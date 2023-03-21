package com.[%= companylower %].[%= namelower %].model;

import java.io.Serializable;
[% if (temporal) { %]import java.util.Date;[% } %]
[% if (oneToMany || manyToMany) { %]import java.util.List;[% } %]
import java.util.Objects;

[% if (oneToMany || manyToOne || manyToMany) { %]import javax.persistence.CascadeType;[% } %]
import javax.persistence.Column;
import javax.persistence.Entity;
[% if (oneToMany || manyToOne || manyToMany) { %]import javax.persistence.FetchType;[% } %]
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
[% if (manyToOne || manyToMany) { %]import javax.persistence.JoinColumn;[% } %]
[% if (manyToMany) { %]import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;[% } %]
[% if (manyToOne) { %]import javax.persistence.ManyToOne;[% } %]
[% if (oneToMany) { %]import javax.persistence.OneToMany;[% } %]
import javax.persistence.Table;
[% if (temporal) { %]import javax.persistence.Temporal;
import javax.persistence.TemporalType;[% } %]

[% if (lombok) { %]import lombok.Getter;
import lombok.Setter;[% } %]
/**
 * Entity class of the table [%= tablename %]
 * 
 * @author [%= username %]
 */
@Entity
@Table(name = "[%= tablename %]")
[% if (lombok) { %]@Getter
@Setter[% } %]
public class [%= namecamel %]DO implements Serializable
{
  private static final long serialVersionUID = 1L;

[% properties.forEach(function(property) { %]
[% if (property.primaryKey) { %]  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "[%= property.column %]")
  private [%- property.type %] [%= property.nameCamel %];
  [% } else if (property.manyToOne) { %]  @ManyToOne
  @JoinColumn(name = "[%= property.manyToOneConfig.joinColumn %]", referencedColumnName = "[%= property.manyToOneConfig.joinReferenceColumn %]")
  private [%- property.type %] [%= property.nameCamel %];
  [% } else if (property.oneToMany) { %]  @OneToMany(mappedBy = "[%= property.oneToManyConfig.mappedBy %]", fetch = FetchType.LAZY)
  private [%- property.type %] [%= property.nameCamel %];
  [% } else if (property.manyToMany) {%][% if (property.manyToManyConfig.mapped) { %]  @ManyToMany(mappedBy = "[%= property.manyToManyConfig.mappedBy %]", fetch = FetchType.LAZY)
  private [%- property.type %] [%= property.nameCamel %];[%} else {%]  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "[%= property.manyToManyConfig.joinTable %]", joinColumns = {
    @JoinColumn(name = "[%= property.manyToManyConfig.joinColumn %]", referencedColumnName = "[%= property.manyToManyConfig.joinReferenceColumn %]") }, inverseJoinColumns = {
        @JoinColumn(name = "[%= property.manyToManyConfig.inverseJoinColumn %]", referencedColumnName = "[%= property.manyToManyConfig.inverseJoinReferenceColumn %]") })
  private [%- property.type %] [%= property.nameCamel %];
  [% } %]
  [% } else if (property.timestamp) { %]  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "[%= property.column %]")
  private [%- property.type %] [%= property.nameCamel %];
  [% } else if (property.time) { %]  @Temporal(TemporalType.TIME)
  @Column(name = "[%= property.column %]")
  private [%- property.type %] [%= property.nameCamel %];
  [% } else if (property.date) { %]  @Temporal(TemporalType.DATE)
  @Column(name = "[%= property.column %]")
  private [%- property.type %] [%= property.nameCamel %];
  [% } else { %]  @Column(name = "[%= property.column %]")
  private [%- property.type %] [%= property.nameCamel %];[% } %]
  [% }); %]

  [% if (!lombok) { %]
    [% properties.forEach(function(property) { %]
  /** 
   * Gets [%= property.name %] 
   */
  public [%- property.type %] get[%= property.name%]()
  {
     return this.[%= property.nameCamel %];
  }

  /**
   * Sets [%= property.name %]
   * @param [%= property.nameCamel %]
   */
  public void set[%= property.name%]([%- property.type %] [%= property.nameCamel %])
  {
    this.[%= property.nameCamel %] = [%= property.nameCamel %];
  }
    [% }); %]
  [% } %]

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals( Object object )
  {
    boolean isEquals = false;
    if( this == object )
    {
      isEquals = true;
    }
    else if( object != null && object.getClass().equals( this.getClass() ) )
    {
      [%= namecamel %]DO that = ([%= namecamel %]DO) object;

      isEquals = Objects.equals( this.id, that.id );
    }
    return isEquals;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode()
  {
    return Objects.hash( this.id );
  }

}
