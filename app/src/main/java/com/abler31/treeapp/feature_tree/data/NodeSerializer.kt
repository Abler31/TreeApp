package com.abler31.treeapp.feature_tree.data

import com.abler31.treeapp.feature_tree.domain.model.Node
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
@Serializer(forClass = Node::class)
object NodeSerializer : KSerializer<Node> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Node") {
        element<String>("name")
        element<Node>("parent")
        element<MutableList<Node>>("children")
    }

    override fun serialize(encoder: Encoder, value: Node) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name)
            encodeSerializableElement(descriptor, 1, NodeSerializer, value.parent ?: Node())
            encodeSerializableElement(descriptor, 2, ListSerializer(NodeSerializer), value.children)
        }
    }

    override fun deserialize(decoder: Decoder): Node {
        return decoder.decodeStructure(descriptor) {
            var name = ""
            var parent: Node? = null
            var children = mutableListOf<Node>()
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> name = decodeStringElement(descriptor, 0)
                    1 -> parent = decodeSerializableElement(descriptor, 1, NodeSerializer)
                    2 -> children = decodeSerializableElement(descriptor, 2, ListSerializer(NodeSerializer)).toMutableList()
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            Node(name, parent, children)
        }
    }
}