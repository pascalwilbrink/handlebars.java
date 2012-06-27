package com.github.edgarespina.handlebars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


/**
 * Unit test for {@link DefaultContext}.
 *
 * @author edgar.espina
 * @since 0.1.0
 */
public class ContextFactoryTest {

  static class Base {
    public String getBaseProperty() {
      return "baseProperty";
    }

    public String getChildProperty() {
      return "baseProperty";
    }
  }

  static class Child extends Base {
    @Override
    public String getChildProperty() {
      return "childProperty";
    }
  }

  @Test
  public void newContext() {
    Context context = Context.newContext("String");
    assertNotNull(context);
    assertEquals("String", context.model());
  }

  @Test(expected = IllegalArgumentException.class)
  public void noContext() {
    Map<String, Object> model = new HashMap<String, Object>();
    Context context = Context.newContext(model);
    assertEquals(context, Context.newContext(context));
  }

  @Test
  public void parentContext() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("name", "Handlebars");
    Context parent = Context.newContext(model);
    assertNotNull(parent);
    assertEquals("Handlebars", parent.get("name"));

    Map<String, Object> extended = new HashMap<String, Object>();
    extended.put("n", "Extended");
    Context child = Context.newContext(parent, extended);
    assertEquals("Extended", child.get("n"));
    assertEquals("Handlebars", child.get("name"));
  }

  @Test(expected = NullPointerException.class)
  public void nullParent() {
    Context.newContext(null, new Object());
  }

  @Test
  public void dotLookup() {
    Context context = Context.newContext("String");
    assertNotNull(context);
    assertEquals("String", context.get("."));
  }

  @Test
  public void thisLookup() {
    Context context = Context.newContext("String");
    assertNotNull(context);
    assertEquals("String", context.get("this"));
  }

  @Test
  public void singleMapLookup() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("simple", "value");
    Context context = Context.newContext(model);
    assertNotNull(context);
    assertEquals("value", context.get("simple"));
  }

  @Test
  public void nestedMapLookup() {
    Map<String, Object> model = new HashMap<String, Object>();
    Map<String, Object> nested = new HashMap<String, Object>();
    model.put("nested", nested);
    nested.put("simple", "value");
    Context context = Context.newContext(model);
    assertNotNull(context);
    assertEquals("value", context.get("nested.simple"));
  }

  @Test
  public void singleObjectLookup() {
    Object model = new Object() {
      @SuppressWarnings("unused")
      public String getSimple() {
        return "value";
      }
    };
    Context context = Context.newContext(model);
    assertNotNull(context);
    assertEquals("value", context.get("simple"));
  }

  @Test
  public void nestedObjectLookup() {
    Object model = new Object() {
      @SuppressWarnings("unused")
      public Object getNested() {
        return new Object() {
          public String getSimple() {
            return "value";
          }
        };
      }
    };
    Context context = Context.newContext(model);
    assertNotNull(context);
    assertEquals("value", context.get("nested.simple"));
  }

  @Test
  public void customLookup() {
    Context context = Context.newContext(new Base());
    assertNotNull(context);
    assertEquals("baseProperty", context.get("baseProperty"));
    assertEquals("baseProperty", context.get("childProperty"));
  }

  @Test
  public void customLookupOnChildClass() {
    Context context = Context.newContext(new Child());
    assertNotNull(context);
    assertEquals("baseProperty", context.get("baseProperty"));
    assertEquals("childProperty", context.get("childProperty"));
  }
}
