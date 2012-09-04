package org.geotools.kml.v22.bindings;

import java.util.List;

import org.geotools.feature.NameImpl;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.Geometries;

import org.geotools.kml.bindings.FeatureTypeBinding;
import org.geotools.kml.v22.KML;
import org.geotools.xml.*;
import org.geotools.xs.XS;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.Schema;

import javax.xml.namespace.QName;

/**
 * Binding object for the type http://www.opengis.net/kml/2.2:SchemaType.
 * 
 * <p>
 * 
 * <pre>
 *  <code>
 *  &lt;complexType final="#all" name="SchemaType"&gt;
 *      &lt;sequence&gt;
 *          &lt;element maxOccurs="unbounded" minOccurs="0" ref="kml:SimpleField"/&gt;
 *          &lt;element maxOccurs="unbounded" minOccurs="0" ref="kml:SchemaExtension"/&gt;
 *      &lt;/sequence&gt;
 *      &lt;attribute name="name" type="string"/&gt;
 *      &lt;attribute name="id" type="ID"/&gt;
 *  &lt;/complexType&gt; 
 * 	
 *   </code>
 * </pre>
 * 
 * </p>
 * 
 * @generated
 */
public class SchemaTypeBinding extends AbstractComplexBinding {

    /**
     * @generated
     */
    public QName getTarget() {
        return KML.SchemaType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated modifiable
     */
    public Class getType() {
        return SimpleFeatureType.class;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
        String featureTypeName = null;
        if (node.hasAttribute("name")) {
            featureTypeName = (String) node.getAttributeValue("name");
        }
        else if (node.hasAttribute("id")) {
            featureTypeName = (String) node.getAttributeValue("name");
        }
        else {
            featureTypeName = "feature";
        }
        tb.setName(featureTypeName);
        //TODO: crs

        for (Node n : (List<Node>) node.getChildren("SimpleField")) {
            String name = (String) n.getAttributeValue("name");
            String typeName = (String) n.getAttributeValue("type");
            tb.add(name, mapTypeName(typeName));
        }
        SimpleFeatureType featureType = tb.buildFeatureType();
        return featureType;
    }

    private Class mapTypeName(String typeName) {
        //try xs simple type
        Schema xsTypeMappingProfile = XS.getInstance().getTypeMappingProfile();
        NameImpl name = new NameImpl(XS.NAMESPACE, typeName);
        if (xsTypeMappingProfile.containsKey(name)) {
            AttributeType type = xsTypeMappingProfile.get(name);
            if (type.getBinding() != null) {
                return type.getBinding();
            }
        }

        //try gml geometry types
        Geometries g = Geometries.getForName(typeName);
        if (g != null) {
            return g.getBinding();
        }

        //default
        return String.class;
    }

}