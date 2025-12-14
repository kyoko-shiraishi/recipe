package com.example.demo.Entities;

public record NormalizeResult(
		boolean matched,
		String rawName,
		String canonicalName
		) {}
