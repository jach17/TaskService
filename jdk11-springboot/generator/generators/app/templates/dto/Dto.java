package com.[%= companylower %].[%= namelower %].commons.dto;

import java.io.Serializable;
[% if (temporal) { %]import java.util.Date;[% } %]
[% if (oneToMany || manyToMany) { %]import java.util.List;[% } %]

[% if (jsonIgnore) { %]import com.fasterxml.jackson.annotation.JsonIgnore;[% } %]
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.v3.oas.annotations.media.Schema;
[% if (lombok) { %]import lombok.Getter;
import lombok.Setter;[% } %]

/**
 * [%= namecamel %] Transfer Object class
 * 
 * @author [%= username %]
 */
[% if (lombok) { %]@Getter
@Setter[% } %]
public class [%= namecamel %]Dto implements Serializable
{
  private static final long serialVersionUID = 1L;

  [% properties.forEach(function(property) { %]
  @Schema(required = true, description = "[%= property.description %]")[% if (property.jsonIgnore) { %]
  @JsonIgnore[% } %]
  private [% if (property.transient) { %]transient[% } %] [%- property.typeDto %] [%= property.nameCamel %];
  [% }); %]

  [% if (!lombok) { %]
    [% properties.forEach(function(property) { %]
  /** 
   * Gets [%= property.name %] 
   */
  public [%- property.typeDto %] get[%= property.name%]()
  {
     return this.[%= property.nameCamel %];
  }

  /**
   * Sets [%= property.name %]
   * @param [%= property.nameCamel %]
   */
  public void set[%= property.name%]([%- property.typeDto %] [%= property.nameCamel %])
  {
    this.[%= property.nameCamel %] = [%= property.nameCamel %];
  }
    [% }); %]
  [% } %]

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    return gson.toJson( this );
  }
}
