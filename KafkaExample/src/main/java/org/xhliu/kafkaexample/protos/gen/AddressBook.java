package org.xhliu.kafkaexample.protos.gen;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;

import java.util.List;

/**
 * Protobuf type {@code AddressBook}
 */
public final class AddressBook extends
        GeneratedMessageV3 implements
        // @@protoc_insertion_point(message_implements:AddressBook)
        AddressBookOrBuilder {
    private static final long serialVersionUID = 0L;

    // Use AddressBook.newBuilder() to construct.
    private AddressBook(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private AddressBook() {
        people_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
            UnusedPrivateParameter unused) {
        return new AddressBook();
    }

    @java.lang.Override
    public final UnknownFieldSet
    getUnknownFields() {
        return this.unknownFields;
    }

    private AddressBook(
            CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        this();
        if (extensionRegistry == null) {
            throw new NullPointerException();
        }
        int mutable_bitField0_ = 0;
        UnknownFieldSet.Builder unknownFields =
                UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10: {
                        if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                            people_ = new java.util.ArrayList<Person>();
                            mutable_bitField0_ |= 0x00000001;
                        }
                        people_.add(
                                input.readMessage(Person.PARSER, extensionRegistry));
                        break;
                    }
                    default: {
                        if (!parseUnknownField(
                                input, unknownFields, extensionRegistry, tag)) {
                            done = true;
                        }
                        break;
                    }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(
                    e).setUnfinishedMessage(this);
        } finally {
            if (((mutable_bitField0_ & 0x00000001) != 0)) {
                people_ = java.util.Collections.unmodifiableList(people_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
        return AddressBookProtos.internal_static_AddressBook_descriptor;
    }

    @java.lang.Override
    protected GeneratedMessageV3.FieldAccessorTable
    internalGetFieldAccessorTable() {
        return AddressBookProtos.internal_static_AddressBook_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        AddressBook.class, AddressBook.Builder.class);
    }

    public static final int PEOPLE_FIELD_NUMBER = 1;
    private List<Person> people_;

    /**
     * <code>repeated .Person people = 1;</code>
     */
    @java.lang.Override
    public List<Person> getPeopleList() {
        return people_;
    }

    /**
     * <code>repeated .Person people = 1;</code>
     */
    @java.lang.Override
    public List<? extends PersonOrBuilder> getPeopleOrBuilderList() {
        return people_;
    }

    /**
     * <code>repeated .Person people = 1;</code>
     */
    @java.lang.Override
    public int getPeopleCount() {
        return people_.size();
    }

    /**
     * <code>repeated .Person people = 1;</code>
     */
    @java.lang.Override
    public Person getPeople(int index) {
        return people_.get(index);
    }

    /**
     * <code>repeated .Person people = 1;</code>
     */
    @java.lang.Override
    public PersonOrBuilder getPeopleOrBuilder(
            int index) {
        return people_.get(index);
    }

    private byte memoizedIsInitialized = -1;

    @java.lang.Override
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        for (int i = 0; i < people_.size(); i++) {
            output.writeMessage(1, people_.get(i));
        }
        unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        for (int i = 0; i < people_.size(); i++) {
            size += com.google.protobuf.CodedOutputStream
                    .computeMessageSize(1, people_.get(i));
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AddressBook)) {
            return super.equals(obj);
        }
        AddressBook other = (AddressBook) obj;

        if (!getPeopleList()
                .equals(other.getPeopleList())) return false;
        if (!unknownFields.equals(other.unknownFields)) return false;
        return true;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        if (getPeopleCount() > 0) {
            hash = (37 * hash) + PEOPLE_FIELD_NUMBER;
            hash = (53 * hash) + getPeopleList().hashCode();
        }
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }

    public static AddressBook parseFrom(
            java.nio.ByteBuffer data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AddressBook parseFrom(
            java.nio.ByteBuffer data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AddressBook parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AddressBook parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AddressBook parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data);
    }

    public static AddressBook parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        return PARSER.parseFrom(data, extensionRegistry);
    }

    public static AddressBook parseFrom(java.io.InputStream input)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static AddressBook parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static AddressBook parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input);
    }

    public static AddressBook parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }

    public static AddressBook parseFrom(
            CodedInputStream input)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input);
    }

    public static AddressBook parseFrom(
            CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
        return GeneratedMessageV3
                .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(AddressBook prototype) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }

    @java.lang.Override
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE
                ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
            GeneratedMessageV3.BuilderParent parent) {
        Builder builder = new Builder(parent);
        return builder;
    }

    /**
     * Protobuf type {@code AddressBook}
     */
    public static final class Builder extends
            GeneratedMessageV3.Builder<Builder> implements
            // @@protoc_insertion_point(builder_implements:AddressBook)
            AddressBookOrBuilder {
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return AddressBookProtos.internal_static_AddressBook_descriptor;
        }

        @java.lang.Override
        protected GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return AddressBookProtos.internal_static_AddressBook_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            AddressBook.class, AddressBook.Builder.class);
        }

        // Construct using AddressBook.newBuilder()
        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(
                GeneratedMessageV3.BuilderParent parent) {
            super(parent);
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            if (GeneratedMessageV3
                    .alwaysUseFieldBuilders) {
                getPeopleFieldBuilder();
            }
        }

        @java.lang.Override
        public Builder clear() {
            super.clear();
            if (peopleBuilder_ == null) {
                people_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
            } else {
                peopleBuilder_.clear();
            }
            return this;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
            return AddressBookProtos.internal_static_AddressBook_descriptor;
        }

        @java.lang.Override
        public AddressBook getDefaultInstanceForType() {
            return AddressBook.getDefaultInstance();
        }

        @java.lang.Override
        public AddressBook build() {
            AddressBook result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        @java.lang.Override
        public AddressBook buildPartial() {
            AddressBook result = new AddressBook(this);
            int from_bitField0_ = bitField0_;
            if (peopleBuilder_ == null) {
                if (((bitField0_ & 0x00000001) != 0)) {
                    people_ = java.util.Collections.unmodifiableList(people_);
                    bitField0_ = (bitField0_ & ~0x00000001);
                }
                result.people_ = people_;
            } else {
                result.people_ = peopleBuilder_.build();
            }
            onBuilt();
            return result;
        }

        @java.lang.Override
        public Builder clone() {
            return super.clone();
        }

        @java.lang.Override
        public Builder setField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                java.lang.Object value) {
            return super.setField(field, value);
        }

        @java.lang.Override
        public Builder clearField(
                com.google.protobuf.Descriptors.FieldDescriptor field) {
            return super.clearField(field);
        }

        @java.lang.Override
        public Builder clearOneof(
                com.google.protobuf.Descriptors.OneofDescriptor oneof) {
            return super.clearOneof(oneof);
        }

        @java.lang.Override
        public Builder setRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                int index, java.lang.Object value) {
            return super.setRepeatedField(field, index, value);
        }

        @java.lang.Override
        public Builder addRepeatedField(
                com.google.protobuf.Descriptors.FieldDescriptor field,
                java.lang.Object value) {
            return super.addRepeatedField(field, value);
        }

        @java.lang.Override
        public Builder mergeFrom(com.google.protobuf.Message other) {
            if (other instanceof AddressBook) {
                return mergeFrom((AddressBook) other);
            } else {
                super.mergeFrom(other);
                return this;
            }
        }

        public Builder mergeFrom(AddressBook other) {
            if (other == AddressBook.getDefaultInstance()) return this;
            if (peopleBuilder_ == null) {
                if (!other.people_.isEmpty()) {
                    if (people_.isEmpty()) {
                        people_ = other.people_;
                        bitField0_ = (bitField0_ & ~0x00000001);
                    } else {
                        ensurePeopleIsMutable();
                        people_.addAll(other.people_);
                    }
                    onChanged();
                }
            } else {
                if (!other.people_.isEmpty()) {
                    if (peopleBuilder_.isEmpty()) {
                        peopleBuilder_.dispose();
                        peopleBuilder_ = null;
                        people_ = other.people_;
                        bitField0_ = (bitField0_ & ~0x00000001);
                        peopleBuilder_ =
                                GeneratedMessageV3.alwaysUseFieldBuilders ?
                                        getPeopleFieldBuilder() : null;
                    } else {
                        peopleBuilder_.addAllMessages(other.people_);
                    }
                }
            }
            this.mergeUnknownFields(other.unknownFields);
            onChanged();
            return this;
        }

        @java.lang.Override
        public final boolean isInitialized() {
            return true;
        }

        @java.lang.Override
        public Builder mergeFrom(
                CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            AddressBook parsedMessage = null;
            try {
                parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                parsedMessage = (AddressBook) e.getUnfinishedMessage();
                throw e.unwrapIOException();
            } finally {
                if (parsedMessage != null) {
                    mergeFrom(parsedMessage);
                }
            }
            return this;
        }

        private int bitField0_;

        private List<Person> people_ =
                java.util.Collections.emptyList();

        private void ensurePeopleIsMutable() {
            if (!((bitField0_ & 0x00000001) != 0)) {
                people_ = new java.util.ArrayList<Person>(people_);
                bitField0_ |= 0x00000001;
            }
        }

        private com.google.protobuf.RepeatedFieldBuilderV3<
                Person, Person.Builder, PersonOrBuilder> peopleBuilder_;

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public List<Person> getPeopleList() {
            if (peopleBuilder_ == null) {
                return java.util.Collections.unmodifiableList(people_);
            } else {
                return peopleBuilder_.getMessageList();
            }
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public int getPeopleCount() {
            if (peopleBuilder_ == null) {
                return people_.size();
            } else {
                return peopleBuilder_.getCount();
            }
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Person getPeople(int index) {
            if (peopleBuilder_ == null) {
                return people_.get(index);
            } else {
                return peopleBuilder_.getMessage(index);
            }
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder setPeople(
                int index, Person value) {
            if (peopleBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensurePeopleIsMutable();
                people_.set(index, value);
                onChanged();
            } else {
                peopleBuilder_.setMessage(index, value);
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder setPeople(
                int index, Person.Builder builderForValue) {
            if (peopleBuilder_ == null) {
                ensurePeopleIsMutable();
                people_.set(index, builderForValue.build());
                onChanged();
            } else {
                peopleBuilder_.setMessage(index, builderForValue.build());
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder addPeople(Person value) {
            if (peopleBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensurePeopleIsMutable();
                people_.add(value);
                onChanged();
            } else {
                peopleBuilder_.addMessage(value);
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder addPeople(
                int index, Person value) {
            if (peopleBuilder_ == null) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensurePeopleIsMutable();
                people_.add(index, value);
                onChanged();
            } else {
                peopleBuilder_.addMessage(index, value);
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder addPeople(
                Person.Builder builderForValue) {
            if (peopleBuilder_ == null) {
                ensurePeopleIsMutable();
                people_.add(builderForValue.build());
                onChanged();
            } else {
                peopleBuilder_.addMessage(builderForValue.build());
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder addPeople(
                int index, Person.Builder builderForValue) {
            if (peopleBuilder_ == null) {
                ensurePeopleIsMutable();
                people_.add(index, builderForValue.build());
                onChanged();
            } else {
                peopleBuilder_.addMessage(index, builderForValue.build());
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder addAllPeople(
                java.lang.Iterable<? extends Person> values) {
            if (peopleBuilder_ == null) {
                ensurePeopleIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(
                        values, people_);
                onChanged();
            } else {
                peopleBuilder_.addAllMessages(values);
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder clearPeople() {
            if (peopleBuilder_ == null) {
                people_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
                onChanged();
            } else {
                peopleBuilder_.clear();
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Builder removePeople(int index) {
            if (peopleBuilder_ == null) {
                ensurePeopleIsMutable();
                people_.remove(index);
                onChanged();
            } else {
                peopleBuilder_.remove(index);
            }
            return this;
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Person.Builder getPeopleBuilder(
                int index) {
            return getPeopleFieldBuilder().getBuilder(index);
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public PersonOrBuilder getPeopleOrBuilder(
                int index) {
            if (peopleBuilder_ == null) {
                return people_.get(index);
            } else {
                return peopleBuilder_.getMessageOrBuilder(index);
            }
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public List<? extends PersonOrBuilder>
        getPeopleOrBuilderList() {
            if (peopleBuilder_ != null) {
                return peopleBuilder_.getMessageOrBuilderList();
            } else {
                return java.util.Collections.unmodifiableList(people_);
            }
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Person.Builder addPeopleBuilder() {
            return getPeopleFieldBuilder().addBuilder(
                    Person.getDefaultInstance());
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public Person.Builder addPeopleBuilder(
                int index) {
            return getPeopleFieldBuilder().addBuilder(
                    index, Person.getDefaultInstance());
        }

        /**
         * <code>repeated .Person people = 1;</code>
         */
        public java.util.List<Person.Builder>
        getPeopleBuilderList() {
            return getPeopleFieldBuilder().getBuilderList();
        }

        private com.google.protobuf.RepeatedFieldBuilderV3<
                Person, Person.Builder, PersonOrBuilder>
        getPeopleFieldBuilder() {
            if (peopleBuilder_ == null) {
                peopleBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                        Person, Person.Builder, PersonOrBuilder>(
                        people_,
                        ((bitField0_ & 0x00000001) != 0),
                        getParentForChildren(),
                        isClean());
                people_ = null;
            }
            return peopleBuilder_;
        }

        @java.lang.Override
        public final Builder setUnknownFields(
                final UnknownFieldSet unknownFields) {
            return super.setUnknownFields(unknownFields);
        }

        @java.lang.Override
        public final Builder mergeUnknownFields(
                final UnknownFieldSet unknownFields) {
            return super.mergeUnknownFields(unknownFields);
        }


        // @@protoc_insertion_point(builder_scope:AddressBook)
    }

    // @@protoc_insertion_point(class_scope:AddressBook)
    private static final AddressBook DEFAULT_INSTANCE;

    static {
        DEFAULT_INSTANCE = new AddressBook();
    }

    public static AddressBook getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated
    public static final Parser<AddressBook>
            PARSER = new AbstractParser<AddressBook>() {
        @java.lang.Override
        public AddressBook parsePartialFrom(
                CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return new AddressBook(input, extensionRegistry);
        }
    };

    public static Parser<AddressBook> parser() {
        return PARSER;
    }

    @java.lang.Override
    public Parser<AddressBook> getParserForType() {
        return PARSER;
    }

    @java.lang.Override
    public AddressBook getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }

}

