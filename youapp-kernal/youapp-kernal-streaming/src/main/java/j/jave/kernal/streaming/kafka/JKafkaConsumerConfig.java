package j.jave.kernal.streaming.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;

import j.jave.kernal.jave.utils.JObjectUtils;

@SuppressWarnings("serial")
public class JKafkaConsumerConfig extends JKafkaConfig{
	
	/**
	 * session.timeout.ms
	 */
	private String sessionTimeoutMs;
	
	/**
	 * request.timeout.ms
	 */
	private String requestTimeoutMs;
	
	/**
	 * enable.auto.commit  "false"/"true"
	 */
	private String enableAutoCommit;
	
	/**
	 * auto.commit.interval.ms
	 */
	private String autoCommitIntervalMs;
	
	
	/**
	 * group.id
	 */
	private String groupId;
	
	/**
	 * key.deserializer
	 * {@link StringDeserializer}
	 */
	private String keyDeserializer;
	
	/**
	 * value.deserializer
	 * {@link StringDeserializer}
	 */
	private String valueDeserializer;
	
	public static JKafkaConsumerConfig build(Map conf){
		JKafkaConfig kafkaConfig=JKafkaConfig.build(conf);
		JKafkaConsumerConfig consumerConfig=JObjectUtils.simpleCopy(kafkaConfig, JKafkaConsumerConfig.class);
		consumerConfig.setAutoCommitIntervalMs(String.valueOf(conf.get("auto.commit.interval.ms")));
		consumerConfig.setEnableAutoCommit(String.valueOf(conf.get("enable.auto.commit")));
		consumerConfig.setGroupId(String.valueOf(conf.get("group.id")));
		consumerConfig.setKeyDeserializer(String.valueOf(conf.get("key.deserializer")));
		consumerConfig.setValueDeserializer(String.valueOf(conf.get("value.deserializer")));
		consumerConfig.setRequestTimeoutMs(String.valueOf(conf.get("request.timeout.ms")));
		consumerConfig.setSessionTimeoutMs(String.valueOf(conf.get("session.timeout.ms")));
		return consumerConfig;
	}
	

	/**
	 * @return the sessionTimeoutMs
	 */
	public String getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	/**
	 * @param sessionTimeoutMs the sessionTimeoutMs to set
	 */
	public void setSessionTimeoutMs(String sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	/**
	 * @return the requestTimeoutMs
	 */
	public String getRequestTimeoutMs() {
		return requestTimeoutMs;
	}

	/**
	 * @param requestTimeoutMs the requestTimeoutMs to set
	 */
	public void setRequestTimeoutMs(String requestTimeoutMs) {
		this.requestTimeoutMs = requestTimeoutMs;
	}

	/**
	 * @return the enableAutoCommit
	 */
	public String getEnableAutoCommit() {
		return enableAutoCommit;
	}

	/**
	 * @param enableAutoCommit the enableAutoCommit to set
	 */
	public void setEnableAutoCommit(String enableAutoCommit) {
		this.enableAutoCommit = enableAutoCommit;
	}

	/**
	 * @return the autoCommitIntervalMs
	 */
	public String getAutoCommitIntervalMs() {
		return autoCommitIntervalMs;
	}

	/**
	 * @param autoCommitIntervalMs the autoCommitIntervalMs to set
	 */
	public void setAutoCommitIntervalMs(String autoCommitIntervalMs) {
		this.autoCommitIntervalMs = autoCommitIntervalMs;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * @return the keyDeserializer
	 */
	public String getKeyDeserializer() {
		return keyDeserializer;
	}

	/**
	 * @param keyDeserializer the keyDeserializer to set
	 */
	public void setKeyDeserializer(String keyDeserializer) {
		this.keyDeserializer = keyDeserializer;
	}

	/**
	 * @return the valueDeserializer
	 */
	public String getValueDeserializer() {
		return valueDeserializer;
	}

	/**
	 * @param valueDeserializer the valueDeserializer to set
	 */
	public void setValueDeserializer(String valueDeserializer) {
		this.valueDeserializer = valueDeserializer;
	}
}
